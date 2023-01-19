package team.devblook.shrimp.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.devblook.shrimp.util.PlayerData;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Set;


@InjectAll
@Command(names = {"sethome"})
public class SetHomeCommand implements CommandClass {
    @Named("players")
    private BukkitConfiguration players;

    private BukkitConfiguration settings;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Command(names = "")
    public void homeCommandEmpty(@Sender Player player, @OptArg("") String nameHome) {
        FileConfiguration playerConfig = players.get();
        FileConfiguration settingsConfig = settings.get();
        PlayerData playerData = new PlayerData();
        if (nameHome.isEmpty() || nameHome.equals(" ")) {
            player.sendMessage("You must put a name to your home");
            return;
        }
        Set<String> keysPlayer = playerConfig.getKeys(false);
        if (keysPlayer.isEmpty()) {
            playerData.setDataPlayer(player, playerConfig, nameHome);
            players.save();
            player.sendMessage("You have created your first home");
            return;
        }
        keysPlayer.forEach(key -> {
            if (key.equals(player.getName())) {
                Set<String> keysHome = playerConfig.getConfigurationSection(key + ".home").getKeys(false);
                if (keysHome.size() > settingsConfig.getInt("max-home-amount")) {
                    player.sendMessage("You have reached the limit of houses");
                    return;
                }
                if (keysHome.stream().anyMatch(keyHome -> keyHome.equals(nameHome))) {
                    player.sendMessage("You already have a home with that namee");
                    return;
                }

                playerData.setDataPlayer(player, playerConfig, nameHome);
                player.sendMessage("You have created a home with the name " + nameHome);
                players.save();
                return;
            }
        });

    }


}
