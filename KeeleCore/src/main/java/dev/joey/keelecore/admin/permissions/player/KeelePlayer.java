package dev.joey.keelecore.admin.permissions.player;

import dev.joey.keelecore.admin.permissions.PlayerRank;
import dev.joey.keelecore.managers.PermissionManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class KeelePlayer {

    private Player player;
    private String name;
    private UUID uuid;
    private PlayerRank rank;
    boolean isVanished = false;
    private transient PermissionAttachment attachment;

    public KeelePlayer(Player player ,PlayerRank rank) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.rank = rank;
    }

    public KeelePlayer(Player player) {
        this.player = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.rank = PlayerRank.PLAYER;
    }

    public KeelePlayer(UUID uuid, String name, PlayerRank rank) {
        this.player = null;
        this.name = name;
        this.uuid = uuid;
        this.rank = rank;
    }

    public KeelePlayer(UUID uuid) {
        this.player = null;
        this.name = null;
        this.uuid = uuid;
        this.rank = PlayerRank.PLAYER;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PlayerRank getRank() {
        return rank;
    }

    public void setRank(PlayerRank rank) {
        this.rank = rank;
    }

    public void setRank(String input) {
        setRank(PlayerRank.valueOf(input.toUpperCase()));
    }

    public void setVanished(boolean vanished) {
        isVanished = vanished;
    }

    public boolean isVanished() {
        return isVanished;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttachment(PermissionAttachment attachment) {
        this.attachment = attachment;
    }

    public PermissionAttachment getAttachment() {
        return this.attachment;
    }
}