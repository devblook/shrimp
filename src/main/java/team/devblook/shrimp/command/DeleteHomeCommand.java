package team.devblook.shrimp.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Command(names = {"delhome", "deletehome"})
public class DeleteHomeCommand implements CommandClass {
    @Inject
    @Named("players")
    private BukkitConfiguration players;

    @Command(names = "", permission = "shrimp.deletehome")
    public void deleteHome(@Sender Player player, String nameHome) {
        FileConfiguration playersConfiguration = players.get();
        String playerPath = player.getName() + ".home";
        Set<String> keys = playersConfiguration.getKeys(false);
        if (!keys.contains(player.getName())) {
            player.sendMessage("§cYou don't have any homes!");
            return;
        }
        Set<String> homes = playersConfiguration.getConfigurationSection(playerPath).getKeys(false);
        if (homes.stream().anyMatch(key -> key.equalsIgnoreCase(nameHome))) {
            for (String homesKey : homes) {
                if (homesKey.equalsIgnoreCase(nameHome)) {
                    playersConfiguration.set(player.getName() + ".home." + homesKey, null);
                    players.save();
                    player.sendMessage("§aHome deleted!");
                    return;
                }
            }
        } else {
            player.sendMessage("§cHome not found!");
        }
    }


}
