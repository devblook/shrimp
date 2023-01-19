package team.devblook.shrimp.command;

import io.papermc.lib.PaperLib;
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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.devblook.shrimp.util.BukkitConfiguration;
import team.unnamed.inject.InjectAll;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Set;

@InjectAll
@Command(names = {"home"})
public class HomeCommand implements CommandClass {


    @Named("players")
    private BukkitConfiguration players;

    private BukkitConfiguration settings;
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Command(names = "")
    public void mainCommand(@Sender Player player,@OptArg String nameHome) {
        FileConfiguration playersConfiguration = players.get();
        Set<String> keys = playersConfiguration.getConfigurationSection(player.getName()).getKeys(false);
        if (keys==null){
            Audience audience = (Audience) player;
            audience.sendMessage(Component.text("You don't have any home").color(NamedTextColor.RED));
            return;
        }
        keys.forEach(key -> {
            if (key.equalsIgnoreCase(nameHome)){
                World world = Bukkit.getWorld(playersConfiguration.getString(player.getName()+"."+key+".world"));
                double x = playersConfiguration.getDouble(player.getName()+"."+key+".x");
                double y = playersConfiguration.getDouble(player.getName()+"."+key+".y");
                double z = playersConfiguration.getDouble(player.getName()+"."+key+".z");
                float yaw = (float) playersConfiguration.getDouble(player.getName()+"."+key+".yaw");
                float pitch = (float) playersConfiguration.getDouble(player.getName()+"."+key+".pitch");
                Location location = new Location(world,x,y,z,yaw,pitch);
                PaperLib.teleportAsync(player,location);
                Audience audience = (Audience) player;
                audience.sendMessage(Component.text("Teleported to "+key).color(NamedTextColor.GREEN));
            }
        });
    }

    @Command(names = "home", permission = "shrimp.home")
    public void toHomeCommand(@Sender Player player, @OptArg String home){
        if (home == null){
            player.sendMessage("You must specify a home");
            return;
        }
        FileConfiguration config = players.get();
        ArrayList<String> keys = new ArrayList<>(config.getKeys(false));
        keys.forEach(k->{
            if(k.equalsIgnoreCase(player.getName())){
                Set<String> subKeys = players.get().getConfigurationSection(k).getKeys(false);
                subKeys.forEach(subKey->{
                    if (subKey.equalsIgnoreCase(home)){
                        World world = Bukkit.getWorld(config.getString(k + "." + subKey + ".world"));
                        double x = config.getDouble(k + "." + subKey + ".x");
                        double y = config.getDouble(k + "." + subKey + ".y");
                        double z = config.getDouble(k + "." + subKey + ".z");
                        float yaw = (float) config.getDouble(k + "." + subKey + ".yaw");
                        float pitch = (float) config.getDouble(k + "." + subKey + ".pitch");
                        Location location = new Location(world, x, y, z, yaw, pitch);
                        player.teleport(location);
                        player.sendMessage("Teleported to home");
                    }
                });
            }
        });

    }

}

