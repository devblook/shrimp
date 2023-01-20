package team.devblook.shrimp.command;

import io.papermc.lib.PaperLib;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        String emptyHomes = messages.get().getString("dont-have-homes");
        Set<String> keys = playersConfiguration.getConfigurationSection(player.getName() + ".home").getKeys(false);
        if (keys.isEmpty() || keys == null) {
            Audience audience = (Audience) player;
            audience.sendMessage(Component.text("You don't have any home").color(NamedTextColor.RED));
            return;
        }
        if (keys.stream().anyMatch(key -> key.equalsIgnoreCase(nameHome))) {
            for (String homesKey : keys) {
                if (homesKey.equalsIgnoreCase(nameHome)) {
                    World world = Bukkit.getWorld(playersConfiguration.getString(player.getName() + ".home." + homesKey + ".world"));
                    double x = playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".x");
                    double y = playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".y");
                    double z = playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".z");
                    float yaw = (float) playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".yaw");
                    float pitch = (float) playersConfiguration.getDouble(player.getName() + ".home." + homesKey + ".pitch");
                    if (settingsConfiguration.getBoolean("teleport-per-world")) {
                        Location location = new Location(world, x, y, z, yaw, pitch);
                        PaperLib.teleportAsync(player, location);
                        return;
                    }
                    if (!player.getWorld().equals(world)) {
                        player.sendMessage("You can't teleport to this home because you are in another world");
                        return;
                    }
                }
            }
            return;
        }
        player.sendMessage("You don't have any home with this name");
        return;


    }


}

