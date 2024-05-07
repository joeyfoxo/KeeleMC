package dev.joey.keelesurvival.server.events.headdrop;

import dev.joey.keelecore.util.UtilClass;
import org.bukkit.DyeColor;
import org.bukkit.entity.*;

public class HeadDropping {


    String encodedTexture;

    public HeadDropping(LivingEntity entity) {


        if (entity.getType() == EntityType.ALLAY) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBlMWM3MDY0YWY3ZGVlNjg2NzdlZmFhOTVmNmU2ZTAxNDMwYjAwNmRkOTE2MzhlYTJhNjE4NDkyNTQ0ODhlYyJ9fX0=";
        }
        if (entity.getType() == EntityType.AXOLOTL) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I4M2EzOGE0NThjM2NjYTA3NjFlMmM4MjEwYzZmNWQyZjMzODBlODYwZDUwZDJmNDc1NjUxNmEyNjQyNjE3ZCJ9fX0=";
        }
        if (entity.getType() == EntityType.BAT) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY4MWE3MmRhNzI2M2NhOWFlZjA2NjU0MmVjY2E3YTE4MGM0MGUzMjhjMDQ2M2ZjYjExNGNiM2I4MzA1NzU1MiJ9fX0=";
        }
        if (entity.getType() == EntityType.BEE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE2MmRkMGI5ZjY1YjU4YTFlNzBmODFkOGUwM2U4ZmY2YzUzZTRlOTg1YmRiZTAxODY1NThkOGE2OWE4MTE4OSJ9fX0=";
        }
        if (entity.getType() == EntityType.BLAZE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==";
        }

        if (entity.getType() == EntityType.CAT) {
            Cat cat = (Cat) entity;
            if (cat.getCatType() == Cat.Type.ALL_BLACK) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MzkyOTNlYmVhYmE0YzM1ZjA4OTM1N2U1NDU3ZjgzOGZhNGE1ZGI0ZGE4M2FmYmZjMjk1YWQyZDZkNmVhNiJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.BRITISH_SHORTHAIR) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZjZDJlMGMyYjVkZjk0ODJlZjQ1ZjVlMWQzY2YwNzc3OGZlYmRkODQ1NWQzZjAyZDMyNjAyOWFkNzlmNDFjZiJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.CALICO) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU1NTIyYzNmMzBlMzY3NDYwNDFmODU0ZWExZmU2NjNiYzkyNjc1MTk0N2FiMWFkNTY1ODFhOGJjNjU3MjI5OSJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.JELLIE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdmNTk2YWZiODY5ZjgwNmY2MTA4NWE0OTliN2U3NTE0ODkxM2QzZTYwNjAxZjE2NjI1OGRmYmNiODJhM2JiZiJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.PERSIAN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ2MmI0YTQ1YzVmYTIzM2E0NjM5YWY2ZmI5MTBhZmMxNzk1ZWIwMDExZTQ2MjY2YTA4NjVkMTczZWJkZTlhYiJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.RAGDOLL) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE5Nzk0NjE2NTAyN2Q2ZThjMjEzNTM0NDNlM2IxMDNlZWY0YmVlMWExYzIzODk4NGZlMGFmYjA2Njg1OWQ0NSJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.RED) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmNiYzBiZjNiY2IyYzUxZWQ0NDUzYjIwZTc0MWE4MGRjNWYwNjU1NTBiMWUzNTFjMGVhZmFkYzUwMjI1N2FmNSJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.SIAMESE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjlmMTY5OTJmM2E5MmMyZTYyZmQzOGQ1ZDg1MWZkNjYxY2ViM2U4YTgxMDIwMDUzNmVhNGIxMmViMDVmMzQxZSJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.TABBY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2IzNTViYTM4OTAxZWU4NmEyM2Q2MWU5Y2UxMDE2NThmMjQxN2Q2YjliNGMzODEyYTkwNGFkZTU4MWNiNDQxNSJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.BLACK) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2EzNzhhODllZTFkNDM1ZmUyZDk0MmQ2ODUzNTExYTgwYzBhOWZhODkyNGM1NTAzZGIzMmU2MjFkNTgzM2FlYiJ9fX0=";
            }
            if (cat.getCatType() == Cat.Type.WHITE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRhOGE3YmY1ZTc1MGY4MDhlNGZiZDQwODk0ZGZlNzQ2YTA3MjQ1ZGMxNmExMTM3NDNhZTM1ZmViNWIwZjc3MSJ9fX0=";
            }
        }

        if (entity.getType() == EntityType.CAVE_SPIDER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNkMThkYWQwYmQ5MGYwMmFjMDg3NTRhNzUzNThiMWMyN2RhNTc5YjNjYTM1YjY5ZTZiYjEwYTdhNWMyZGJkYSJ9fX0=";
        }

        if (entity.getType() == EntityType.CHICKEN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2EzNTgyY2U0ODg5MzMzZGFkMzI5ZTRlMjQzNzJhMDNhNWRhYTJjMzQyODBjNTYyNTZhZjUyODNlZGIwNDNmOCJ9fX0=";
        }
        if (entity.getType() == EntityType.COD) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0=";
        }
        if (entity.getType() == EntityType.COW) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY1NTE4NDA5NTVmNTI0MzY3NTgwZjExYjM1MjI4OTM4YjY3ODYzOTdhOGYyZThjOGNjNmIwZWIwMWI1ZGIzZCJ9fX0=";
        }
        if (entity.getType() == EntityType.CREEPER) {
            Creeper creeper = (Creeper) entity;
            if (creeper.isPowered()) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJjZWIzOWRkNGRlMjRhN2FkZmUyOTFhM2EwY2ZjN2NmNGY2NDVkZTU5YjYwM2ZjZmUwNmM2YjVhMDZlMjYifX19";
            } else {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNTQ4MzhjMzNlYTIyN2ZmY2EyMjNkZGRhYWJmZTBiMDIxNWY3MGRhNjQ5ZTk0NDQ3N2Y0NDM3MGNhNjk1MiJ9fX0=";
            }
        }

        if (entity.getType() == EntityType.DOLPHIN) {
            if (UtilClass.percentageChance(0.0040D)) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQyMDhmYTBmYTY5MDFlYjIzZmUwZGU0YTAyYjU3Y2I2NjFhYzllZDlkNGYwMWRmZGYyZDA3MzM5NzEwNWVjMSJ9fX0=";
            } else {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ==";
            }
        }

        if (entity.getType() == EntityType.DONKEY) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiNmMzYzA1MmNmNzg3ZDIzNmEyOTE1ZjgwNzJiNzdjNTQ3NDk3NzE1ZDFkMmY4Y2JjOWQyNDFkODhhIn19fQ==";
        }

        if (entity.getType() == EntityType.DROWNED) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg0ZGY3OWM0OTEwNGIxOThjZGFkNmQ5OWZkMGQwYmNmMTUzMWM5MmQ0YWI2MjY5ZTQwYjdkM2NiYmI4ZTk4YyJ9fX0=";
        }
        if (entity.getType() == EntityType.ELDER_GUARDIAN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkyMDg5NjE4NDM1YTBlZjYzZTk1ZWU5NWE5MmI4MzA3M2Y4YzMzZmE3N2RjNTM2NTE5OWJhZDMzYjYyNTYifX19";
        }
        if (entity.getType() == EntityType.ENDERMAN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1OWJiMGE3YTMyOTY1YjNkOTBkOGVhZmE4OTlkMTgzNWY0MjQ1MDllYWRkNGU2YjcwOWFkYTUwYjljZiJ9fX0=";
        }
        if (entity.getType() == EntityType.ENDERMITE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0=";
        }
        if (entity.getType() == EntityType.EVOKER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk1NDEzNWRjODIyMTM5NzhkYjQ3ODc3OGFlMTIxMzU5MWI5M2QyMjhkMzZkZDU0ZjFlYTFkYTQ4ZTdjYmE2In19fQ==";
        }
        if (entity.getType() == EntityType.FOX) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTRhNDJlNjllMDg4MWFlNmQyNGQ0MjgxNDU5YzE0NGEwZDVhOTY4YWVkMzVkNmQzZDczYTNjNjVkMjZhIn19fQ==";
        }
        if (entity.getType() == EntityType.FROG) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE3ODE4OWVmOGZhN2E1YjcyNGZiOTFkZjlhNDQ3ODRmZDg1NjQ4ZWQzZTNhY2Y2ZDBkZWQ3YjhjYWEzMGYwNyJ9fX0=";
        }
        if (entity.getType() == EntityType.GOAT) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAzMzMwMzk4YTBkODMzZjUzYWU4YzlhMWNiMzkzYzc0ZTlkMzFlMTg4ODU4NzBlODZhMjEzM2Q0NGYwYzYzYyJ9fX0=";
        }
        if (entity.getType() == EntityType.GHAST) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmEyNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19";
        }
        if (entity.getType() == EntityType.GUARDIAN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==";
        }

        if (entity.getType() == EntityType.HORSE) {

            Horse horse = (Horse) entity;
            if (horse.getColor() == Horse.Color.WHITE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY0YmRkNTlkNGY4ZjFkNTc4MmUwZmVlNGJkNjRhZWQxMDA2MjdmMTg4YTkxNDg5YmEzN2VlYWRlZGVkZDgyNyJ9fX0=";
            }
            if (horse.getColor() == Horse.Color.CREAMY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZkYWUwYWRlMGUwZGFmYjZkYmM3Nzg2Y2U0MjQxMjQyYjZiNmRmNTI3YTBmN2FmMGE0MjE4NGM5M2ZkNjQ2YiJ9fX0=";
            }
            if (horse.getColor() == Horse.Color.CHESTNUT) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk5NjM5OWZmZjljYmNmYjdiYTY3N2RkMGMyZDEwNDIyOWQxY2MyMzA3YTZmMDc1YTg4MmRhNDY5NGVmODBhZSJ9fX0=";
            }
            if (horse.getColor() == Horse.Color.BROWN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjVlMzk3ZGVmMGFmMDZmZWVmMjI0MjE4NjAwODgxODY2Mzk3MzJhYTBhNWViNTc1NmUwYWE2YjAzZmQwOTJjOCJ9fX0=";
            }
            if (horse.getColor() == Horse.Color.BLACK) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VmYjBiOTg1N2Q3YzhkMjk1ZjZkZjk3YjYwNWY0MGI5ZDA3ZWJlMTI4YTY3ODNkMWZhM2UxYmM2ZTQ0MTE3In19fQ==";
            }
            if (horse.getColor() == Horse.Color.GRAY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYwZDk1NTg4OWIwMzc4ZDQ5MzNjOTU2Mzk4NTY3ZTc3MDEwM2FlOWVmZjBmNzAyZDBkNTNkNTJlN2Y2YTgzYiJ9fX0=";
            }
            if (horse.getColor() == Horse.Color.DARK_BROWN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTU2YjdiYzFhNDgzNmViNDI4ZWE4OTI1ZWNlYjVlMDFkZmJkMzBjN2RlZmY2Yzk0ODI2ODk4MjMyMDNjZmQyZiJ9fX0=";
            }
        }

        if (entity.getType() == EntityType.SKELETON_HORSE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ==";
        }
        if (entity.getType() == EntityType.ZOMBIE_HORSE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIyOTUwZjJkM2VmZGRiMThkZTg2ZjhmNTVhYzUxOGRjZTczZjEyYTZlMGY4NjM2ZDU1MWQ4ZWI0ODBjZWVjIn19fQ==";
        }
        if (entity.getType() == EntityType.HOGLIN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0=";
        }
        if (entity.getType() == EntityType.HUSK) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY3NGM2M2M4ZGI1ZjRjYTYyOGQ2OWEzYjFmOGEzNmUyOWQ4ZmQ3NzVlMWE2YmRiNmNhYmI0YmU0ZGIxMjEifX19";
        }
        if (entity.getType() == EntityType.IRON_GOLEM) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19";
        }
        if (entity.getType() == EntityType.LLAMA) {

            Llama llama = (Llama) entity;
            if (UtilClass.percentageChance(0.00040D)) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc1ZGVmOTc4N2Y4YWJiYjhmMmYxODkzMjIxMzQwMDgwZTU5NDU0NGRjYWU1NWYwNThiNmQzMzA2MmJhMDI2OCJ9fX0=";
            }
            if (llama.getColor() == Llama.Color.GRAY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQyZmZjZTlhMTc0ZmUxYzA4NGUyZDgyMDUyMTgyZDk0Zjk1ZWQ0MzZiNzVmZjdlYTdhNGU5NGQ5NGM3MmQ4YSJ9fX0=";
            }
            if (llama.getColor() == Llama.Color.CREAMY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFlMjVkZGMyZDI1MzljNTY1ZGZmMmFhNTAwNjAzM2YxNGNjMDYzNzlmZTI4YjA3MzFjN2JkYzY1YmEwZTAxNiJ9fX0=";
            }
            if (llama.getColor() == Llama.Color.BROWN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y4MzI0NjZkY2M3ZDVlNzcwMmNkZWU0Y2Q1NTVkYmQzOTYzN2QyMGFkZjkzNjdmYjAzY2ZkNjg4OGJhYWFlNyJ9fX0=";
            }
            if (llama.getColor() == Llama.Color.WHITE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNkOWI1OTE1OTEyZmZjMmI4NTc2MWQ2YWRjYjQyOGE4MTJmOWI4M2ZmNjM0ZTMzMTE2MmNlNDZjOTllOSJ9fX0=";
            }
        }

        if (entity.getType() == EntityType.MAGMA_CUBE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0=";
        }

        if (entity.getType() == EntityType.MUSHROOM_COW) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ==";
        }
        if (entity.getType() == EntityType.MULE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19";
        }
        if (entity.getType() == EntityType.OCELOT) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTAxMmI2OGQ3NDc1Nzc5ZTE1YTZjMjA1NDViMTU3OWFhZmY0ODRiN2FiOGMxOTVhZGE4NDQxZDg0MDgyZGJiZCJ9fX0=";
        }
        if (entity.getType() == EntityType.PANDA) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2ZTNhZDgyM2Y5NmQ0YTgwYTE0NTU2ZDhjOWM3NjMyMTYzYmJkMmE4NzZjMDExOGI0NTg5MjVkODdhNTUxMyJ9fX0=";
        }
        if (entity.getType() == EntityType.PARROT) {
            Parrot parrot = (Parrot) entity;
            if (parrot.getVariant() == Parrot.Variant.GRAY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNjMzQ3MjJhYzY0NDk2YzliODRkMGM1NDAxOWRhYWU2MTg1ZDYwOTQ5OTAxMzNhZDY4MTBlZWEzZDI0MDY3YSJ9fX0=";
            }
            if (parrot.getVariant() == Parrot.Variant.RED) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQxYTE2OGJjNzJjYjMxNGY3Yzg2ZmVlZjlkOWJjNzYxMjM2NTI0NGNlNjdmMGExMDRmY2UwNDIwMzQzMGMxZCJ9fX0=";
            }
            if (parrot.getVariant() == Parrot.Variant.BLUE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMDNiMTBjMTVlZTU2MDE0MjM4NjdkZmI4YmNiY2JjOTE5Y2E5NmMwZWVhNjMwNzNlYzhlNzk1ZWFiZDA1ZiJ9fX0=";
            }
            if (parrot.getVariant() == Parrot.Variant.CYAN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM2NDcxZjIzNTQ3YjJkYmRmNjAzNDdlYTEyOGY4ZWIyYmFhNmE3OWIwNDAxNzI0ZjIzYmQ0ZTI1NjRhMmI2MSJ9fX0=";
            }
            if (parrot.getVariant() == Parrot.Variant.GREEN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZjOWEzYjlkNTg3OWMyMTUwOTg0ZGJmZTU4OGNjMmU2MWZiMWRlMWU2MGZkMmE0NjlmNjlkZDRiNmY2YTk5MyJ9fX0=";
            }
        }

        if (entity.getType() == EntityType.PHANTOM) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQ2ODMwZGE1ZjgzYTNhYWVkODM4YTk5MTU2YWQ3ODFhNzg5Y2ZjZjEzZTI1YmVlZjdmNTRhODZlNGZhNCJ9fX0=";
        }
        if (entity.getType() == EntityType.PIG) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=";
        }
        if (entity.getType() == EntityType.PIGLIN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg4MmFmMTI5NGE3NDAyM2U2OTE5YTMxZDFhMDI3MzEwZjJlMTQyYWZiNDY2N2QyMzBkMTU1ZTdmMjFkYmI0MSJ9fX0=";
        }
        if (entity.getType() == EntityType.PIGLIN_BRUTE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzMDBlOTAyNzM0OWM0OTA3NDk3NDM4YmFjMjllM2E0Yzg3YTg0OGM1MGIzNGMyMTI0MjcyN2I1N2Y0ZTFjZiJ9fX0=";
        }
        if (entity.getType() == EntityType.PILLAGER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFlZTZiYjM3Y2JmYzkyYjBkODZkYjVhZGE0NzkwYzY0ZmY0NDY4ZDY4Yjg0OTQyZmRlMDQ0MDVlOGVmNTMzMyJ9fX0=";
        }
        if (entity.getType() == EntityType.POLAR_BEAR) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRmZTkyNjkyMmZiYjQwNmYzNDNiMzRhMTBiYjk4OTkyY2VlNDQxMDEzN2QzZjg4MDk5NDI3YjIyZGUzYWI5MCJ9fX0=";
        }
        if (entity.getType() == EntityType.PUFFERFISH) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkyMzUwYzlmMDk5M2VkNTRkYjJjNzExMzkzNjMyNTY4M2ZmYzIwMTA0YTliNjIyYWE0NTdkMzdlNzA4ZDkzMSJ9fX0=";
        }

        if (entity.getType() == EntityType.RABBIT) {
            Rabbit rabbit = (Rabbit) entity;
            if (rabbit.getRabbitType() == Rabbit.Type.BLACK) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlhNjc1ZWRiM2NiYTBmMzQzNmFlOTQ3M2NmMDM1OTJiN2E0OWQzODgxMzU3OTA4NGQ2MzdlNzY1OTk5OWI4ZSJ9fX0=";
            }
            if (rabbit.getRabbitType() == Rabbit.Type.WHITE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBkY2RkYzIzNjk3MmVkY2Q0OGU4MjViNmIwMDU0YjdiNmUxYTc4MWU2ZjEyYWUwNGMxNGEwNzgyN2NhOGRjYyJ9fX0=";
            }
            if (rabbit.getRabbitType() == Rabbit.Type.BROWN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzFkYjM4ZWYzYzFhMWQ1OWY3NzlhMGNkOWY5ZTYxNmRlMGNjOWFjYzc3MzRiOGZhY2MzNmZjNGVhNDBkMDIzNSJ9fX0=";
            }
            if (rabbit.getRabbitType() == Rabbit.Type.GOLD) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE2MzYxZmVhMjRiMTExZWQ3OGMxZmVmYzI5NTIxMmU4YTU5YjBjODhiNjU2MDYyNTI3YjE3YTJkNzQ4OWM4MSJ9fX0=";
            }
            if (rabbit.getRabbitType() == Rabbit.Type.BLACK_AND_WHITE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJmMzllMGE2MDMzODZjYTFlZTM2MjM2ZTBiNDkwYTE1NDdlNmUyYTg5OTExNjc0NTA5MDM3ZmI2ZjcxMTgxMCJ9fX0";
            }
            if (rabbit.getRabbitType() == Rabbit.Type.SALT_AND_PEPPER) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M0MzQ5ZmU5OTAyZGQ3NmMxMzYxZjhkNmExZjc5YmZmNmY0MzNmM2I3YjE4YTQ3MDU4ZjBhYTE2YjkwNTNmIn19fQ==";
            }
            if (rabbit.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWY5ODMzN2I4MjQyMjI5ZDk1ZGEyMzA5MDc1NTc4OTc3OGIxOGJmNWQwN2Q2MWY2MjBjZGJkYmJkMjlmYTYxNSJ9fX0=";
            }
        }
        if (entity.getType() == EntityType.RAVAGER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM3M2UxNmZhMjkyNjg5OWNmMTg0MzQzNjBlMjE0NGY4NGVmMWViOTgxZjk5NjE0ODkxMjE0OGRkODdlMGIyYSJ9fX0=";
        }
        if (entity.getType() == EntityType.SALMON) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZiZGYxMDMxN2E3YTYzMTVjM2NiYWY3ZWRjNTkwZjBkOTc3MTRiZDY5OWM4NjE1YTI0ZjcyYmI4YTliYyJ9fX0=";
        }
        if (entity.getType() == EntityType.SHEEP) {

            Sheep sheep = (Sheep) entity;
            if (sheep.getColor() == DyeColor.WHITE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkyZGYyMTZlY2QyNzYyNGFjNzcxYmFjZmJmZTAwNmUxZWQ4NGE3OWU5MjcwYmUwZjg4ZTljODc5MWQxZWNlNCJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.LIGHT_GRAY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRhNTliZTYyMGFlOGIzZWUwZGQwZmEyMmM4MGFmZmVkNGEwZjcyOTI5NWNiOGM0MWU3OGVlNzgzZjQ2MzNhZCJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.GRAY) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZjMmEyNzU1YjIwZGRmZjU1MWE2OTAzZjJkYzdlNjFmMTNlYmUzOWIxZDVjYTkyOWM4N2JkODU4M2VjODAxZiJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.BLACK) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjM0YWM1YjM5OGNmN2M4NmUzZjZmMTg4YTUxMjdkOGIyODNkNzcyYmY1ODg1YzcwZTBjMTMwODA1ZjA2OTk1MCJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.BROWN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTU4MTM3MTVjMmYzNGYwNTY0OWY4ZmEzZWFhYTY3ZjFlZGE1ZTZmOWNmOTMwZmE5YzJlMDQxMmQxZjk3MjhlMSJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.PURPLE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQzY2JkYWUxZjIwYTc5MjgxZDNhNzFhZGYyNDJhMzVjOGNjNTg1NjJiNDE1ZjExMjBiY2E5ZDk0Yjc2ZjI1NCJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.MAGENTA) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUyMjhiMDRlOWI5NzlhMTBiNzBiOGRiNmYzZmIxOTlkZWViNTgxNTk0YTVhYTRhN2ZlYmU5NDhkYjE3MjI4YiJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.PINK) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmU3Y2YxYzU4ZGJiN2MzMjU1Yjk0YzYwNDNmYThmMGQ3NzZjMTM0ZjRkOThiODFjYTMxNDEwOTY1ZjQ3YTI1YSJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.LIGHT_BLUE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhlYjBkMTc0Nzk4NzBiMzk3M2U4ZTAwMWI4MmRjZGUyMmVmYzlkMTBjOTA0MTJjNjczM2EwYjEzNjU2NGQxZiJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.CYAN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA1NTgzODdiNjY1OGY1ZTlkY2ZmYzcxOTIxNGI2MDNmNjAzYzRiMDRlNzA4YjdhYWJlNzViY2FlOTFlODA0YyJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.BLUE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5ZWZjNGI0ZWFkZWM0ODU3NmE1NzAwZWM4MTIzOTU1MTAzMjdlNWQxZTdjMTA4ZmQ4YWJjNzc5NjY4NWFhMyJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.GREEN) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTc1M2E4ZWMzMmJlOWM1NTBkMWM1NjBhY2I5NDFlZGQ5ZTNiNzNkZGJmMTU4NjkyM2ZiMzdiMjIwYjQ1NTNkZCJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.LIME) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNlNDA5MGUxYmNjZjk5MmIzNmRlZjc0YTZkN2QzOTcyYzE3ZGIxYjc1NTU0ZTJjNTA5MjcxNjgwYjhlNzk3NCJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.YELLOW) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJhNTM1NGMyMzBlODYxYWFjNzI3MzRhNDU4MmQxMzE3MDI2NDU0YjgwN2FjMzUzZmMzYTBiZDBkOGM0MjJiYSJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.ORANGE) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDI3MTQ0MmQ4YTM3ZGI0OWYwMmE5NGMyOTM1MjY5NDk2MmI1ZDBiZDZiZWEwNWYxZDkzZmUxOWViNGU3MDYwZSJ9fX0=";
            }
            if (sheep.getColor() == DyeColor.RED) {
                encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBjZTViNWNhOTE2NWFjNzdhOWMzZTNmNjRkZjBkMzE3MGQ1YWZjZjlkNWE1NTc1ZTNmMGMwZjIxZTQzYjgzIn19fQ==";
            }

        }

        if (entity.getType() == EntityType.SHULKER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJjNjQyY2ZlMzM4MTQ3NjdkNjkyZDI3NTk5YzhiZWY0ZjVhMzA2ZmMyMTBkNGI1MGE1ODBiNzA0MGYwMmIxOCJ9fX0=";
        }
        if (entity.getType() == EntityType.SILVERFISH) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==";
        }
        if (entity.getType() == EntityType.SLIME) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIwZTg0ZDMyZDFlOWM5MTlkM2ZkYmI1M2YyYjM3YmEyNzRjMTIxYzU3YjI4MTBlNWE0NzJmNDBkYWNmMDA0ZiJ9fX0=";
        }
        if (entity.getType() == EntityType.SNOWMAN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZkZmQxZjc1MzhjMDQwMjU4YmU3YTkxNDQ2ZGE4OWVkODQ1Y2M1ZWY3MjhlYjVlNjkwNTQzMzc4ZmNmNCJ9fX0=";
        }
        if (entity.getType() == EntityType.SPIDER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==";
        }
        if (entity.getType() == EntityType.SQUID) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg3MDU2MjRkYWEyOTU2YWE0NTk1NmM4MWJhYjVmNGZkYjJjNzRhNTk2MDUxZTI0MTkyMDM5YWVhM2E4YjgifX19";
        }
        if (entity.getType() == EntityType.GLOW_SQUID) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjZDBiNWViNmIzODRkYjA3NmQ4NDQ2MDY1MjAyOTU5ZGRkZmYwMTYxZTBkNzIzYjNkZjBjYzU4NmQxNmJiZCJ9fX0=";
        }
        if (entity.getType() == EntityType.STRAY) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhkZGY3NmU1NTVkZDVjNGFhOGEwYTVmYzU4NDUyMGNkNjNkNDg5YzI1M2RlOTY5ZjdmMjJmODVhOWEyZDU2In19fQ==";
        }
        if (entity.getType() == EntityType.STRIDER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0=";
        }
        if (entity.getType() == EntityType.SKELETON) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==";
        }
        if (entity.getType() == EntityType.TADPOLE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNjOWI5NzQwYmQzYWRlYmE1MmUwY2UwYTc3YjNkZmRlZjhkM2E0MDU1NWE0ZThiYjY3ZDIwMGNkNjI3NzBkMCJ9fX0=";
        }
        if (entity.getType() == EntityType.TROPICAL_FISH) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYzODlhY2Q3YzgyODBkMmM4MDg1ZTZhNmE5MWUxODI0NjUzNDdjYzg5OGRiOGMyZDliYjE0OGUwMjcxYzNlNSJ9fX0=";
        }
        if (entity.getType() == EntityType.TURTLE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ==";
        }
        if (entity.getType() == EntityType.VEX) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ==";
        }
        if (entity.getType() == EntityType.VILLAGER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIyZDhlNzUxYzhmMmZkNGM4OTQyYzQ0YmRiMmY1Y2E0ZDhhZThlNTc1ZWQzZWIzNGMxOGE4NmU5M2IifX19";
        }
        if (entity.getType() == EntityType.VINDICATOR) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGY2ZmI4OWQxYzYzMWJkN2U3OWZlMTg1YmExYTY3MDU0MjVmNWMzMWE1ZmY2MjY1MjFlMzk1ZDRhNmY3ZTIifX19";
        }
        if (entity.getType() == EntityType.WARDEN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzViMjZjYThkMjUzOGY3ZjZkZjA4YzgxZjBhZGI4MzA1ODAxNmZiNDE5M2I5YWE1M2ZkMDRiNDEyMDAwYWZkMiJ9fX0=";
        }
        if (entity.getType() == EntityType.WITCH) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0=";
        }
        if (entity.getType() == EntityType.WITHER_SKELETON) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk1M2I2YzY4NDQ4ZTdlNmI2YmY4ZmIyNzNkNzIwM2FjZDhlMWJlMTllODE0ODFlYWQ1MWY0NWRlNTlhOCJ9fX0=";
        }
        if (entity.getType() == EntityType.WITHER) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmNzRlMzIzZWQ0MTQzNjk2NWY1YzU3ZGRmMjgxNWQ1MzMyZmU5OTllNjhmYmI5ZDZjZjVjOGJkNDEzOWYifX19";
        }
        if (entity.getType() == EntityType.WOLF) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0=";
        }
        if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0=";
        }
        if (entity.getType() == EntityType.ZOGLIN) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3ZTE4NjAyZTAzMDM1YWQ2ODk2N2NlMDkwMjM1ZDg5OTY2NjNmYjllYTQ3NTc4ZDNhN2ViYmM0MmE1Y2NmOSJ9fX0=";
        }
        if (entity.getType() == EntityType.ZOMBIE) {
            encodedTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==";
        }

    }

    public String getEncodedTexture() {
        return encodedTexture;
    }




}
