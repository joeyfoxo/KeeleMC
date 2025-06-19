package dev.joey.keelecore.admin.permissions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public enum PlayerRank {
    OWNER(6, "&c&lOWNER &r", "", List.of(
            "velocity.command.plugins",
            "velocity.command.info",
            "velocity.command.reload",
            "velocity.command.dump",
            "velocity.command.heap",
            "velocity.command.server",
            "velocity.command.shutdown",
            "velocity.command.glist",
            "minecraft.command.op",
            "minecraft.command.deop",
            "velocity.command.send",
            "keelecore.bypass"
    )),
    DEV(5, "&4&lDEV &r", "", List.of(
            "velocity.command.plugins",
            "velocity.command.info",
            "velocity.command.reload",
            "velocity.command.dump",
            "velocity.command.heap",
            "velocity.command.server",
            "minecraft.command.op",
            "minecraft.command.deop",
            "velocity.command.send",
            "keelecore.debug"
    )),
    ADMIN(4, "&4&lADMIN &r", "", List.of(
            "velocity.command.plugins",
            "velocity.command.info",
            "velocity.command.reload",
            "velocity.command.dump",
            "velocity.command.heap",
            "velocity.command.server",
            "velocity.command.send"
    )),
    MOD(3, "&b&lMOD &r", "", List.of(
            "velocity.command.server",
            "velocity.command.send",
            "velocity.command.glist",
            "keelecore.warn"
    )),
    HELPER(2, "&d&lHELPER &r", "", List.of(
            "velocity.command.glist",
            "keelecore.help"
    )),
    STUDENT(1, "&a&lSTUDENT &r", "", List.of()),
    GUEST(0, "&9&lGUEST &r", "", List.of()),
    PLAYER(0, "&8&lGUEST &r", "", List.of());

    private final int level;
    private final String prefix;
    private final String suffix;
    private final List<String> permissions;

    PlayerRank(int level, String prefix, String suffix, List<String> permissions) {
        this.level = level;
        this.prefix = prefix;
        this.suffix = suffix;
        this.permissions = permissions;
    }

    public int getLevel() {
        return level;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public static List<String> getPermissionsForRank(String rank) {
        PlayerRank r = fromString(rank);
        return r != null ? r.getPermissions() : List.of();
    }

    public static boolean rankHasPermission(String rank, String permission) {
        PlayerRank r = fromString(rank);
        return r != null && r.getPermissions().contains(permission);
    }

    public Component getPrefix() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
    }

    public Component getSuffix() {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(suffix);
    }

    public String getColorCode() {
        if (prefix.length() >= 2 && prefix.startsWith("&")) {
            return prefix.substring(0, 2);
        }
        return "&f"; // Default to white if not found
    }

    // --- Rank Comparison using level ---

    public boolean hasPermissionLevel(PlayerRank required) {
        return this.level >= required.level;
    }

    public boolean hasPermissionLevel(String rankString) {
        PlayerRank required = fromString(rankString);
        return required != null && hasPermissionLevel(required);
    }

    public boolean isStaff() {
        return this.level >= HELPER.level;
    }

    // --- Helpers ---

    public static PlayerRank fromString(String input) {
        try {
            return PlayerRank.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public static List<String> listRanks() {
        return Arrays.stream(values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}