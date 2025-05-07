package dev.joeyfox.cravingChaos.game;

import dev.joeyfox.cravingChaos.CravingChaos;
import dev.joeyfoxo.core.game.CoreGameStart;
import dev.joeyfoxo.core.game.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class CravingGameStart extends CoreGameStart<CravingGame> {

    private final World world = Bukkit.getWorld("world");
    private final Location center = new Location(world, 0, 100, 0);

    CravingGame game;

    public CravingGameStart(CravingGame game) {
        super(game);
        this.game = game;

        game.setTeamed(false); // Disable team logic

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    buildGlassBox();
                    startCountdown();
                    cancel();
                }
            }
        }.runTaskTimer(CravingChaos.getKeeleMiniCore(), 0L, 20L);
    }

    @Override
    protected void startCountdown() {
        super.startCountdown();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (countdownSeconds <= 0) {
                    game.setGameStatus(GameStatus.IN_GAME);
                    cancel();
                }
            }
        }.runTaskTimer(CravingChaos.getKeeleMiniCore(), 0L, 20L);
    }

    private void buildGlassBox() {
        int size = 3;
        for (int x = -size; x <= size; x++) {
            for (int y = 0; y <= size * 2; y++) {
                for (int z = -size; z <= size; z++) {
                    boolean edge = x == -size || x == size || y == 0 || y == size * 2 || z == -size || z == size;
                    if (edge) {
                        world.getBlockAt(center.clone().add(x, y, z)).setType(Material.GLASS);
                    } else {
                        world.getBlockAt(center.clone().add(x, y, z)).setType(Material.AIR);
                    }
                }
            }
        }
    }
}