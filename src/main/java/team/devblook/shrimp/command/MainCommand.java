package team.devblook.shrimp.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@InjectAll
@Command(names = {"shrimp"})
public class MainCommand implements CommandClass {

    private BukkitConfiguration settings;
    @Named("messages")
    private BukkitConfiguration messages;

    private UserHandler userHandler;

    @Command(names = "list", permission = "shrimp.list")
    public void getHomesListCommand(@Sender Player player) {
        User user = userHandler.get(player.getUniqueId().toString());
        if (user == null) {
            throw new IllegalStateException("User is null");
        }
        Collection<Home> homeCollection = user.homes();
        if (homeCollection.isEmpty()) {
            player.sendMessage(messages.getMessage("list-homes-empty"));
            return;
        }
        Component component = messages.getMessage("homes");
        for (Home home : homeCollection) {
            Component homeComponent = Component
                    .text(home.name() + "  ")
                    .color(NamedTextColor.AQUA)
                    .decoration(TextDecoration.ITALIC, false)
                    .hoverEvent(HoverEvent.showText(messages.getMessage("click-to-teleport")))
                    .clickEvent(ClickEvent.runCommand("/shrimp:home " + home.name()));
            component = component.append(homeComponent);
        }
        player.sendMessage(component);


    }

    @Command(names = "reload", permission = "shrimp.op")
    public void reloadCommand(@Sender Player player) {
        settings.reload();
        messages.reload();
        player.sendMessage("Reloaded...");
    }

    @Command(names = "help", permission = "shrimp.help")
    public void helpCommand(@Sender Player player) {
        List<String> help = new ArrayList<>(messages.get().getStringList("help-messages"));
        for (String message : help) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(message));
        }

    }
}
