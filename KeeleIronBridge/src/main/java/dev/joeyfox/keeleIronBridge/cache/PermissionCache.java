package dev.joeyfox.keeleIronBridge.cache;

import java.util.*;

public class PermissionCache {
    private final Map<UUID, Set<String>> permissionMap = new HashMap<>();

    public void update(UUID uuid, List<String> perms) {
        permissionMap.put(uuid, new HashSet<>(perms));
    }

    public boolean hasPermission(UUID uuid, String permission) {
        return permissionMap.getOrDefault(uuid, Set.of()).contains(permission);
    }

    public List<String> getPermissions(UUID uuid) {
        return permissionMap.get(uuid) == null ? List.of() : new ArrayList<>(permissionMap.get(uuid));
    }

    public void clear() {
        permissionMap.clear();
    }

    public void remove(UUID uuid) {
        permissionMap.remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return permissionMap.containsKey(uuid);
    }

    public void add(UUID uuid, String permission) {
        permissionMap.computeIfAbsent(uuid, k -> new HashSet<>()).add(permission);
    }
}