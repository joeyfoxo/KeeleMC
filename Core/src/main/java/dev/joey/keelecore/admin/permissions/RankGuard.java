package dev.joey.keelecore.admin.permissions;


import dev.joey.keelecore.admin.permissions.player.KeelePlayer;

import java.lang.reflect.Method;

public class RankGuard {

    // Preferred: check both method and class level
    public static boolean hasRequiredRank(KeelePlayer player, Object instance, String methodName, Object... parameters) {
        try {
            Method method = findMatchingMethod(instance.getClass(), methodName, parameters);
            RequireRank annotation = method != null ? method.getAnnotation(RequireRank.class) : null;

            if (annotation != null) {
                return player.getRank().hasPermissionLevel(annotation.value());
            }

            // Fallback to class-level check
            RequireRank classAnnotation = instance.getClass().getAnnotation(RequireRank.class);
            if (classAnnotation != null) {
                return player.getRank().hasPermissionLevel(classAnnotation.value());
            }

            return true; // No restrictions

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Simple version for just class-level checks (used in commands, if desired)
    public static boolean hasRequiredRank(Object instance, KeelePlayer player) {
        RequireRank annotation = instance.getClass().getAnnotation(RequireRank.class);
        if (annotation == null) return true;
        return player.getRank().hasPermissionLevel(annotation.value());
    }

    private static Method findMatchingMethod(Class<?> clazz, String methodName, Object[] args) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.getName().equals(methodName)) continue;
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != args.length) continue;

            boolean matches = true;
            for (int i = 0; i < paramTypes.length; i++) {
                if (args[i] == null || !paramTypes[i].isAssignableFrom(args[i].getClass())) {
                    matches = false;
                    break;
                }
            }

            if (matches) return method;
        }
        return null;
    }
}