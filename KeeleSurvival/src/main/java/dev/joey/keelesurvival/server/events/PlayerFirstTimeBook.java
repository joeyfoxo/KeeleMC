package dev.joey.keelesurvival.server.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import static dev.joey.keelesurvival.util.Util.keeleSurvival;

public class PlayerFirstTimeBook implements CommandExecutor, Listener {

    public PlayerFirstTimeBook() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        player.getInventory().addItem(getCustomBook(player.getWorld()));

        return false;
    }
    @EventHandler
    public void onJoinFirstTime(PlayerJoinEvent event) {

        if (!event.getPlayer().hasPlayedBefore()) {
            event.getPlayer().getInventory().setItem(4, getCustomBook(event.getPlayer().getWorld()));
        }

    }

    private ItemStack getCustomBook(World world) {

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setTitle(ChatColor.translateAlternateColorCodes('&', "&aIntroduction"));
        meta.setAuthor("KeeleMC");
        meta.setGeneration(BookMeta.Generation.ORIGINAL);
        meta.addPages(Component.text().content("     Welcome to")
                        .color(TextColor.color(62, 237, 61))
                        .decorate(TextDecoration.BOLD)

                        .append(Component.text().content("\n"))
                        .append(Component.text().content("      Keele MC \n\n\n").color(TextColor.color(181, 75, 0)))
                        .append(Component.text().content("Welcome to survival, here you will find something for everyone, " +
                                        "over the next few pages you will find all that this server has to offer!")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("       World\n\n")
                        .color(TextColor.color(112, 0, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Information\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("Difficulty: ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(0, 108, 231)))
                        .append(Component.text().content(world.getDifficulty() + "\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))

                        .append(Component.text().content("Border Size: ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(0, 108, 231)))
                        .append(Component.text().content(world.getWorldBorder().getSize() / 2 + "\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("       World\n\n")
                        .color(TextColor.color(112, 0, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Dragon Spawning\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("As we understand many players want to fight the dragon so it will spawn every week" +
                                        " upon killing it the loot you receive will vary based on the damage inflicted")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("       World\n\n")
                        .color(TextColor.color(112, 0, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Meteorites\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("Meteorites will have a chance to land every 4 hours, so make sure" +
                                        " to keep an eye out for when one lands to gain some valuable loot and get to it before other players do or it " +
                                        "cools down")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("       World\n\n")
                        .color(TextColor.color(112, 0, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("TNT Explosions\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("On this survival we have made TNT more realistic which means it will throw " +
                                        "10% of the blocks exploded into the air to land, just like a real explosion")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("       World\n\n")
                        .color(TextColor.color(112, 0, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Sleeping\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("To skip the night only half the server need to sleep however lying in a bed to" +
                                        " attempt to sleep will ensure phantoms won't attack you")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("       World\n\n")
                        .color(TextColor.color(112, 0, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Head Drops\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("Most mobs have a low percentage to drop their head upon being slain" +
                                        " this also extends to players which have a 10% of dropping their head")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("     Bounties\n\n")
                        .color(TextColor.color(62, 237, 61))
                        .decorate(TextDecoration.BOLD)

                        .append(Component.text().content("Commands\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("/bounty \n\n/bounty <set/add> <Player> <Amount>\n\n/bounty <get> <Player>")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("     Bounties\n\n")
                        .color(TextColor.color(62, 237, 61))
                        .decorate(TextDecoration.BOLD)

                        .append(Component.text().content("Usage\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("When a player with a bounty is killed, the killer will get the bounty if they are the person to deal the final blow. " +
                                        "The victim may also drop their head which can be sold")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("     Bounties\n\n")
                        .color(TextColor.color(62, 237, 61))
                        .decorate(TextDecoration.BOLD)

                        .append(Component.text().content("Usage\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("You can check whether theres a bounty on your head via the" +
                                        "colour of your name, it will be red if there is a bounty otherwise white")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("      Claiming\n\n")
                        .color(TextColor.color(32, 178, 237))
                        .decorate(TextDecoration.BOLD)

                        .append(Component.text().content("Commands\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("/claim <size> \n\n/unclaim\n\n/abandonallclaims\n\n/claimlist")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("      Claiming\n\n")
                        .color(TextColor.color(32, 178, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Claims\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("To claim a section of land use ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content("/claim <size>")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content(" to specify a size of an area to claim or ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content("/claim")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content(" to claim a default size")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("      Claiming\n\n")
                        .color(TextColor.color(32, 178, 237))
                        .decorate(TextDecoration.BOLD)

                        .append(Component.text().content("Containers\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("/access <set/add> <Player> \n\n/access remove <Player>")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("      Claiming\n\n")
                        .color(TextColor.color(32, 178, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Containers\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("When claiming an area please be aware that this doesn't lock the chests," +
                                        " to secure your items use ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content("/lock")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content(" to lock a chest or ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content("/unlock")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(220, 0, 0)))
                        .append(Component.text().content(" to unlock the chest and allow access")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content("    Interaction\n\n")
                        .color(TextColor.color(217, 124, 237))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Sit, Crawl, Lay\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("To sit on chairs or block just simply right click it or use")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content(" /sit\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(255, 0, 0)))

                        .append(Component.text().content("Fancy sneaking into small spaced double space or type")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content(" /crawl\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(255, 0, 0)))
                        .append(Component.text().content("Fancy having a lay down use")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content(" /lay\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(255, 0, 0)))
                        .build()

                , Component.text().content("      Economy\n\n")
                        .color(TextColor.color(237, 0, 0))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content("Gaining Money\n").color(TextColor.color(62, 237, 61)))
                        .append(Component.text().content("Its always helpful to have a good" +
                                        " sum of money to be able to buy more items this can be done by using ")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .append(Component.text().content("/sell")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(0, 0, 255)))

                        .append(Component.text().content(" to sell blocks and ores or by selling mob and player heads" +
                                        " plus many other ways such as bounties")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))
                        .build()

                , Component.text().content(" Server Commands\n\n")
                        .color(TextColor.color(237, 157, 0))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content(
                                        "/spawn - Teleports to spawn \n" +
                                                "/discord - Shows discord link\n" +
                                                "/hub - Teleports to hub\n " +
                                                "/wilderness - Teleports to the wild\n" +
                                                "/sell - Sells a player head for money\n" +
                                                "/balance - Shows your current balance\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))

                        .build()

                , Component.text().content(" Server Commands\n\n")
                        .color(TextColor.color(237, 157, 0))
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text().content(
                                        "/lock - Locks a chest for players \n" +
                                                "/unlock - Unlocks a chest\n" +
                                                "/access - Modifies chest permissions\n " +
                                                "/bounty - Allows setting of bounties\n" +
                                                "/nextclear - Shows when the next clearlag is\n" +
                                                "/rules - Opens rules\n")
                                .decoration(TextDecoration.BOLD, false)
                                .color(TextColor.color(50, 50, 50)))

                        .build()


        );

        book.setItemMeta(meta);

        return book;

    }
}
