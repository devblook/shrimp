package team.devblook.shrimp.command;

import jdk.jshell.execution.Util;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
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
        if (nameHome.isEmpty()||nameHome.equals(" ")) {
            player.sendMessage("Debes poner un nombre a tu home");
            return;
        }
        Set<String> keysPlayer = playerConfig.getKeys(false);
        if (keysPlayer.isEmpty()){
            player.sendMessage("empty");
            return;
        }
        keysPlayer.forEach(key->{
            if (key.equals(player.getName())){
                Set<String> keysHome = playerConfig.getConfigurationSection(key+".home").getKeys(false);
                if (keysHome.size()+1>settingsConfig.getInt("max-home-amount")){
                    player.sendMessage("Has alcanzado el limite de homes");
                    return;
                }
                if (keysHome.stream().anyMatch(keyHome->keyHome.equals(nameHome))){
                    player.sendMessage("Ya tienes un home con ese nombre");
                    return;
                }
                PlayerData playerData = new PlayerData();
                playerData.setDataPlayer(player,playerConfig,nameHome);
                player.sendMessage("Has creado un home con el nombre "+nameHome);
                players.save();
                return;
            }
        });

    }


}
