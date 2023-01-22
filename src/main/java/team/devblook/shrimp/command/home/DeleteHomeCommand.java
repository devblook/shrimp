package team.devblook.shrimp.command.home;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.user.UserHandler;

import javax.inject.Inject;

@Command(names = {"delhome", "deletehome"})
public class DeleteHomeCommand implements CommandClass {

    @Inject
    private UserHandler userHandler;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Command(names = "", permission = "shrimp.deletehome")
    public void deleteHome(@Sender Player player, String nameHome) {
        User user = userHandler.get(player.getUniqueId().toString());

        if (user == null) {
            throw new IllegalStateException("User is null");
        }

        if (!user.hasHome(nameHome)) {
            player.sendMessage(MINI_MESSAGE.deserialize("<red>You don't have a home with that name"));
            return;
        }

        user.removeHome(nameHome);
        userHandler.update(user);
        player.sendMessage(MINI_MESSAGE.deserialize("<green>You have deleted a home with the name <gold>" + nameHome));
    }


}
