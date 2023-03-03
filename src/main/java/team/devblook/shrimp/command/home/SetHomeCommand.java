package team.devblook.shrimp.command.home;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = {"sethome"})
public class SetHomeCommand implements CommandClass {

    @Inject
    private BukkitConfiguration settings;
    @Inject
    private UserHandler userHandler;
    @Inject
    @Named("messages")
    private BukkitConfiguration messages;


    @Command(names = "")
    public void homeCommandEmpty(@Sender Player player, @OptArg("") String nameHome) {
        FileConfiguration settingsConfig = settings.get();

        if (nameHome.isEmpty() || nameHome.equals(" ")) {
            player.sendMessage(messages.getMessage("set-home-empty-name"));
            return;
        }

        int maxHomeAmount = settingsConfig.getInt("max-home-amount");
        User user = userHandler.get(player.getUniqueId().toString());

        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (user.sizeHomes() >= maxHomeAmount) {
            player.sendMessage(messages.getMessage("limit-homes"));
            return;
        }

        if (user.hasHome(nameHome)) {
            player.sendMessage(messages.getMessage("equals-homes-name"));
            return;
        }

        user.addHome(nameHome, player.getLocation());
        userHandler.update(user);
        player.sendMessage(messages.getMessage("create-home" + nameHome));
    }


}
