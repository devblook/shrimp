package team.devblook.shrimp.command;

import io.papermc.lib.PaperLib;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Set;

@InjectAll
@Command(names = {"home"})
public class HomeCommand implements CommandClass {

    @Named("messages")
    private BukkitConfiguration messages;
    @Named("players")
    private BukkitConfiguration players;

    private BukkitConfiguration settings;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Command(names = "")
    public void mainCommand(@Sender Player player, @OptArg String nameHome) {
        FileConfiguration playersConfiguration = players.get();
        FileConfiguration settingsConfiguration = settings.get();
        FileConfiguration messagesConfiguration = messages.get();
        String HOMES_EMPTY = messagesConfiguration.getString("dont-have-homes");
        String HOME_ANOTHER_WORLD = messagesConfiguration.getString("teleport-per-world");
        String HOME_NOT_FOUND = messagesConfiguration.getString("home-doesnt-exist");
        String HOME_TELEPORTED = messagesConfiguration.getString("home-teleported");
        Set<String> keys = playersConfiguration.getKeys(false);
        boolean crossWorld = settingsConfiguration.getBoolean("teleport-per-world");
        if (!keys.contains(player.getName())) {
            player.sendMessage(HOMES_EMPTY);
            return;
        }
        String playerPath = player.getName() + ".home";
        Set<String> homes = playersConfiguration.getConfigurationSection(playerPath).getKeys(false);
        if (homes.stream().anyMatch(key -> key.equalsIgnoreCase(nameHome))) {
            for (String homesKey : homes) {
                if (homesKey.equalsIgnoreCase(nameHome)) {
                    double x = playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".x");
                    double y = playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".y");
                    double z = playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".z");
                    float yaw = (float) playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".yaw");
                    float pitch = (float) playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".pitch");
                    World world = Bukkit.getWorld(playersConfiguration.getString(player.getName() + ".home." + homesKey + ".world"));
                    if (crossWorld) {
                        Location location = new Location(world, x, y, z, yaw, pitch);
                        PaperLib.teleportAsync(player, location);
                        player.sendMessage(HOME_TELEPORTED + homesKey + "!");
                        return;
                    }
                    if (!player.getWorld().equals(world)) {
                        player.sendMessage(HOME_ANOTHER_WORLD);
                        return;
                    }
                    PaperLib.teleportAsync(player, new Location(world, x, y, z, yaw, pitch));
                    player.sendMessage(HOME_TELEPORTED + homesKey + "!");
                    return;
                }

            }
            return;
        }
        player.sendMessage(HOME_NOT_FOUND);
        return;


    }


}

