package team.devblook.shrimp.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Set;

@InjectAll
@Command(names = {"shrimp"})
public class MainCommand implements CommandClass {

    private BukkitConfiguration settings;
    @Named("messages")
    private BukkitConfiguration messages;
    @Named("players")
    private BukkitConfiguration players;

    @Command(names = "list", permission = "shrimp.list")
    public void getHomesListCommand(@Sender Player player) {
        FileConfiguration playersConfiguration = players.get();
        Set<String> keys = playersConfiguration.getKeys(false);
        if (!keys.contains(player.getName())) {
            player.sendMessage("You don't have any homes");
            return;
        }
        Set<String> homes = playersConfiguration.getConfigurationSection(player.getName() + ".home").getKeys(false);
        Component component = Component.text("Homes: ").color(NamedTextColor.GOLD);
        for (String home : homes) {
            Component homeComponent = Component
                    .text(home + "  ")
                    .color(NamedTextColor.AQUA)
                    .decoration(TextDecoration.ITALIC, false)
                    .hoverEvent(HoverEvent.showText(Component.text("Click to teleport to " + home)
                            .color(NamedTextColor.GOLD)))
                    .clickEvent(ClickEvent.runCommand("/shrimp:home " + home));
            component = component.append(homeComponent);

        }
        Audience audience = (Audience) player;
        audience.sendMessage(component);


    }

    @Command(names = "reload", permission = "shrimp.op")
    public void reloadCommand(@Sender Player player) {
        player.sendMessage("?");
        players.reload();
        settings.reload();
        messages.reload();
        player.sendMessage("Reloaded...");
    }

    @Command(names = "help", permission = "shrimp.help")
    public void helpCommand(@Sender Player player) {
        //TODO: help command
    }
}
