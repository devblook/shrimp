package team.devblook.shrimp.command.home;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.home.HomePosition;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = {"home"})
public class HomeCommand implements CommandClass {

    @Inject
    private UserHandler userHandler;
    @Inject
    @Named("messages")
    private BukkitConfiguration messages;

    @Command(names = "")
    public void mainCommand(@Sender Player player, @OptArg String nameHome) {

        User user = userHandler.get(player.getUniqueId().toString());

        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (nameHome.isEmpty()) {
            player.sendMessage(messages.getMessage("empty-name-homes"));
            return;
        }

        if (!user.hasHome(nameHome)) {
            player.sendMessage(messages.getMessage("home-dont-exist"));
            return;
        }

        Home home = user.home(nameHome);

        if (home == null) {
            throw new IllegalStateException("Home is null");
        }

        Location location = HomePosition.Positions.toLocation(home.position());
        player.teleportAsync(location);
        player.sendMessage(messages.getMessage("teleport-to-home" + nameHome));
    }
}

