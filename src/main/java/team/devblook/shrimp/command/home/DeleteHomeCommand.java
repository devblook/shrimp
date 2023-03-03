package team.devblook.shrimp.command.home;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

@Command(names = {"delhome", "deletehome"})
public class DeleteHomeCommand implements CommandClass {

    @Inject
    private UserHandler userHandler;
    @Inject
    @Named("messages")
    private BukkitConfiguration messages;


    @Command(names = "", permission = "shrimp.deletehome")
    public void deleteHome(@Sender Player player, String nameHome) {
        User user = userHandler.get(player.getUniqueId().toString());

        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (!user.hasHome(nameHome)) {
            player.sendMessage(messages.getMessage("home-dont-exist"));
            return;
        }

        user.removeHome(nameHome);
        userHandler.update(user);
        player.sendMessage(messages.getMessage("delete-home" + nameHome));
    }


}
