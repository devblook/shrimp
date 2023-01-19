package team.devblook.shrimp.util;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PlayerData {
    public void setDataPlayer(Player player, FileConfiguration fileConfiguration, String nameHome) {
        Location locationPlayer = player.getLocation();
        double x = locationPlayer.getX();
        double y = locationPlayer.getY();
        double z = locationPlayer.getZ();
        float yaw = locationPlayer.getYaw();
        float pitch = locationPlayer.getPitch();
        String world = player.getWorld().getName();
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".x", x);
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".y", y);
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".z", z);
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".yaw", yaw);
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".pitch", pitch);
        fileConfiguration.set(player.getName() + ".home." + nameHome + ".world", world);
    }
}
