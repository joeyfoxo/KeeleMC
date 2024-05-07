package dev.joeyfoxo.core.game.teams;

import org.bukkit.Material;

public enum TeamColors {

    RED(Material.RED_WOOL),
    YELLOW(Material.YELLOW_WOOL),
    BLUE(Material.BLUE_WOOL),
    GREEN(Material.GREEN_WOOL);

    private final Material woolMaterial;

    TeamColors(Material woolMaterial) {
        this.woolMaterial = woolMaterial;
    }

    public Material getWoolMaterial() {
        return this.woolMaterial;
    }
}