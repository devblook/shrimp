package team.devblook.shrimp.command.home;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;

@Command(names = {"sethome"})
public class SetHomeCommand implements CommandClass {

    @Inject
    private BukkitConfiguration settings;
    @Inject
    private UserHandler userHandler;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Command(names = "")
    public void homeCommandEmpty(@Sender Player player, @OptArg("") String nameHome) {
        FileConfiguration settingsConfig = settings.get();

        if (nameHome.isEmpty() || nameHome.equals(" ")) {
            player.sendMessage("You must put a name to your home");
            return;
        }

        int maxHomeAmount = settingsConfig.getInt("max-home-amount");
        User user = userHandler.get(player.getUniqueId().toString());

        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (user.sizeHomes() >= maxHomeAmount) {
            player.sendMessage(MINI_MESSAGE.deserialize("<red>You have reached the limit of houses"));
            return;
        }

        if (user.hasHome(nameHome)) {
            player.sendMessage(MINI_MESSAGE.deserialize("<red>You already have a home with that name"));
            return;
        }

        user.addHome(nameHome, player.getLocation());
        userHandler.update(user);
        player.sendMessage(MINI_MESSAGE.deserialize("<green>You have created a home with the name <gold>" + nameHome));
    }


}
