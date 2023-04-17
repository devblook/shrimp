package team.devblook.shrimp.command.home;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import io.papermc.lib.PaperLib;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.home.HomePosition;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitAudience;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(
        value = "home",
        alias = {"home", "h"}
)
@Permission("shrimp.home")
public class HomeCommand extends BaseCommand {

    @Inject
    private UserHandler userHandler;
    @Inject
    @Named("messages")
    private BukkitConfiguration messages;

    @Inject
    private Shrimp plugin;


    @Default//@Command(names = "")
    public void mainCommand(Player player, @Suggestion("homes") String nameHome) {

        User user = userHandler.get(player.getUniqueId().toString());
        BukkitAudience audience = new BukkitAudience(plugin);

        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (nameHome.isEmpty()) {
            audience.adventure().player(player).sendMessage((messages.getMessage("empty-name-homes")));
            return;
        }

        if (!user.hasHome(nameHome)) {
            audience.adventure().player(player).sendMessage(messages.getMessage("home-dont-exist"));
            return;
        }

        Home home = user.home(nameHome);

        if (home == null) {
            throw new IllegalStateException("Home is null");
        }

        Location location = HomePosition.Positions.toLocation(home.position());
        PaperLib.teleportAsync(player, location);

        Component component = messages.getMessage("teleport-to-home").replaceText(builder -> builder.match("%home%").replacement(nameHome));
        audience.adventure().player(player).sendMessage(component);

    }
}

