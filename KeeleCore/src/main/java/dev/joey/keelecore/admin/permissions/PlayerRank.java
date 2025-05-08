package dev.joey.keelecore.admin.permissions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public enum PlayerRank {
        OWNER("&c&lOWNER &r", "", List.of(
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
        DEV("&4&lDEV &r", "", List.of(
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
        ADMIN("&4&lADMIN &r", "", List.of(
            "velocity.command.plugins",
            "velocity.command.info",
            "velocity.command.reload",
            "velocity.command.dump",
            "velocity.command.heap",
            "velocity.command.server",
            "velocity.command.send"
        )),
        MOD("&b&lMOD &r", "", List.of(
            "velocity.command.server",
            "velocity.command.send",
            "velocity.command.glist",
            "keelecore.warn"
        )),
        HELPER("&d&lHELPER &r", "", List.of(
            "velocity.command.glist",
            "keelecore.help"
        )),
        STUDENT("&a&lSTUDENT &r", "", List.of()),
        GUEST("&9&lGUEST &r", "", List.of()),
        PLAYER("&8&lGUEST &r", "", List.of());

    private final String prefix;
    private final String suffix;
    private final List<String> permissions;

    PlayerRank(String prefix, String suffix, List<String> permissions) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.permissions = permissions;
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

    // --- Rank Comparison ---

    public boolean hasPermissionLevel(PlayerRank required) {
        boolean result = this.ordinal() >= required.ordinal();
        System.out.println("[DEBUG] " + this + ".hasPermissionLevel(" + required + ") = " + result);

        return result;
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