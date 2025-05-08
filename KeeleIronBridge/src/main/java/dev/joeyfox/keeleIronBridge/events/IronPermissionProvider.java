package dev.joeyfox.keeleIronBridge.events;

import com.velocitypowered.api.permission.PermissionFunction;
import com.velocitypowered.api.permission.PermissionProvider;
import com.velocitypowered.api.permission.PermissionSubject;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;

import java.util.*;

public class IronPermissionProvider  implements PermissionProvider {

    private static final Map<UUID, Set<String>> permissions = new HashMap<>();

    public static void setPermissions(UUID uuid, List<String> perms) {
        permissions.put(uuid, new HashSet<>(perms));
    }

    public static boolean hasPermission(UUID uuid, String perm) {
        return permissions.getOrDefault(uuid, Set.of()).contains(perm);
    }

    public static void addPermission(UUID uuid, String permission) {
        permissions.computeIfAbsent(uuid, k -> new HashSet<>()).add(permission);
    }

    public static Set<String> getPermissions(UUID uuid) {
        return permissions.getOrDefault(uuid, Set.of());
    }

    @Override
    public PermissionFunction createFunction(PermissionSubject subject) {
        return permission -> {
            if (!(subject instanceof com.velocitypowered.api.proxy.Player)) {
                return Tristate.UNDEFINED;
            }

            UUID uuid = ((Player) subject).getUniqueId();

            return hasPermission(uuid, permission)
                    ? Tristate.TRUE
                    : Tristate.FALSE;
        };
    }
}
