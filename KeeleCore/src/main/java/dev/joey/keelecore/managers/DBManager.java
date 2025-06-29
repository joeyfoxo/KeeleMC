package dev.joey.keelecore.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.joey.keelecore.KeeleCore;
import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.admin.permissions.formatting.NameTagFormatting;
import dev.joey.keelecore.admin.permissions.player.KeelePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DBManager {
    private final HikariDataSource dataSource;

    private static DBManager manager;

    public DBManager(JavaPlugin plugin) {

        if (manager == null) {
            manager = this;
        }
        FileConfiguration config = plugin.getConfig();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + config.getString("database.host") + ":" +
                config.getInt("database.port") + "/" + config.getString("database.name") +
                "?useSSL=false&allowPublicKeyRetrieval=true");
        hikariConfig.setUsername(config.getString("database.user"));
        hikariConfig.setPassword(config.getString("database.password"));
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("KeeleMCPool");

        dataSource = new HikariDataSource(hikariConfig);

    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null) dataSource.close();
    }

    // -- ASYNC SAVE or UPDATE
    public void savePlayerDataAsync(UUID uuid, String name, PlayerRank rank, boolean vanished) {
        System.out.println("[DB] Saving player data for " + name + " (" + uuid + ") with rank " + rank.name());
        Bukkit.getScheduler().runTaskAsynchronously(KeeleCore.getInstance(), () -> {
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "REPLACE INTO players (uuid, name, `rank`, vanished) VALUES (?, ?, ?, ?)")) {
                stmt.setString(1, uuid.toString());
                stmt.setString(2, name);
                stmt.setString(3, rank.name());
                stmt.setBoolean(4, vanished);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<KeelePlayer> loadPlayerAsync(UUID uuid) {
        System.out.println("[DB] Attempting to load player data for UUID: " + uuid);
        CompletableFuture<KeelePlayer> future = new CompletableFuture<>();

        Bukkit.getScheduler().runTaskAsynchronously(KeeleCore.getInstance(), () -> {
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT name, `rank`, vanished FROM players WHERE uuid = ?")) {

                System.out.println("[DB] Connected to DB successfully for load.");
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String rankStr = rs.getString("rank");
                    boolean vanished = rs.getBoolean("vanished");
                    PlayerRank rank = PlayerRank.valueOf(rankStr);

                    System.out.println("[DB] Found DB entry for " + uuid + ", name: " + name + ", rank: " + rankStr);

                    // If online, bind the live player
                    KeelePlayer player = new KeelePlayer(uuid, name, rank);

                    player.setVanished(vanished);
                    player.setRank(rank);

                    Bukkit.getScheduler().runTask(KeeleCore.getInstance(), () -> future.complete(player));
                    return;
                }
                else {

                    System.out.println("[DB] No player record found for UUID: " + uuid);
                    KeelePlayer keelePlayer = new KeelePlayer(uuid);
                    Bukkit.getScheduler().runTask(KeeleCore.getInstance(), () -> future.complete(keelePlayer));

                }
            } catch (SQLException | IllegalArgumentException e) {
                e.printStackTrace();
            }

            Bukkit.getScheduler().runTask(KeeleCore.getInstance(), () -> future.complete(null));
        });

        return future;
    }

    // -- ASYNC UPDATE RANK
    public void updateRankAsync(UUID uuid, PlayerRank newRank) {
        System.out.println("[DB] Updating rank for " + uuid + " to " + newRank.name());
        Bukkit.getScheduler().runTaskAsynchronously(KeeleCore.getInstance(), () -> {
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE players SET `rank` = ? WHERE uuid = ?")) {
                stmt.setString(1, newRank.name());
                stmt.setString(2, uuid.toString());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // -- ASYNC DELETE
    public void deletePlayerAsync(KeelePlayer player) {
        System.out.println("[DB] Deleting player data for " + player.getName() + " (" + player.getUuid() + ")");
        Bukkit.getScheduler().runTaskAsynchronously(KeeleCore.getInstance(), () -> {
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM players WHERE uuid = ?")) {
                stmt.setString(1, player.getUuid().toString());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // -- ASYNC EXISTS

    public CompletableFuture<Boolean> playerExistsAsync(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[DB] Checking existence of player with UUID: " + uuid);

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM players WHERE uuid = ? LIMIT 1")) {
                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();
                boolean exists = rs.next();
                System.out.println("[DB] Player exists: " + exists);
                return exists;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public static DBManager getInstance() {
        if (manager == null) {
            throw new IllegalStateException("DBManager has not been initialized. Call the constructor first.");
        }
        return manager;
    }
}