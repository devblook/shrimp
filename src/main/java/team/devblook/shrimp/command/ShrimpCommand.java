package team.devblook.shrimp.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;

@Command(names = {"shrimp"})
public class ShrimpCommand implements CommandClass {

    @Command(names = "")
    public void mainCommand(@Sender Player player) {
        player.sendMessage("command");
    }
}
