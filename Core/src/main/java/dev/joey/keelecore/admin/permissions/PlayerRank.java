package dev.joey.keelecore.admin.permissions;

import java.util.Arrays;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public enum PlayerRank {
    OWNER("&c&lOWNER &r", ""),
    DEV("&c&lDEV &r", ""),
    ADMIN("&4&lADMIN &r", ""),
    MOD("&b&lMOD &r", ""),
    HELPER("&d&lHELPER &r", ""),
    STUDENT("&a&lSTUDENT &r", ""),
    GUEST("&9&lGUEST &r", ""),
    PLAYER("&8&lGUEST &r", "");

    private final String prefix;
    private final String suffix;

    PlayerRank(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public Component getPrefix() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
    }

    public Component getSuffix() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(suffix);
    }

    // --- Rank Comparison ---

    public boolean hasPermissionLevel(PlayerRank other) {
        return this.ordinal() <= other.ordinal();
    }

    public boolean hasPermissionLevel(String rankString) {
        PlayerRank other = fromString(rankString);
        return other != null && hasPermissionLevel(other);
    }

    // --- Helpers ---

    public static PlayerRank fromString(String input) {
        try {
            return PlayerRank.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public static String listRanks() {
        return Arrays.stream(values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.joining(", "));
    }

    public boolean isStaff() {
        return this.ordinal() <= HELPER.ordinal();
    }
}