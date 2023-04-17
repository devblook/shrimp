package team.devblook.shrimp.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.util.BukkitAudience;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@InjectAll
@Command(
        value = "shrimp",
        alias = {"shrimp"}
)
@Permission("shrimp.op")
public class MainCommand extends BaseCommand {

    private BukkitConfiguration settings;
    @Named("messages")
    private BukkitConfiguration messages;

    @Inject
    private Shrimp plugin;

    //@Command(names = "list", permission = "shrimp.list")

    //@Command(names = "reload", permission = "shrimp.op")
    @SubCommand("reload")
    public void reloadCommand(Player player) {
        settings.reload();
        messages.reload();
        player.sendMessage("Reloaded...");
    }

    //@Command(names = "help", permission = "shrimp.help")
    @SubCommand("help")
    public void helpCommand(Player player) {
        List<String> help = new ArrayList<>(messages.get().getStringList("help-messages"));
        BukkitAudience playerAudience = new BukkitAudience(this.plugin);
        for (String message : help) {
            playerAudience.adventure().player(player).sendMessage(MiniMessage.miniMessage().deserialize(message));
        }

    }
}
