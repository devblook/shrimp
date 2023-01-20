package team.devblook.shrimp.util;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

public class HomeData {
    @Inject
    @Named("players")
    private  BukkitConfiguration players;
    public void setDataPlayer(Player player, FileConfiguration fileConfiguration, String nameHome) {
        Location location = player.getLocation();
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".x", location.getX());
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".y", location.getY());
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".z", location.getZ());
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".yaw", location.getYaw());
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".pitch", location.getPitch());
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".world", location.getWorld().getName());
        players.save();
    }
}
