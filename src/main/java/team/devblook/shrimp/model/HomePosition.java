package team.devblook.shrimp.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public record HomePosition(String world, double x, double y, double z, float yaw, float pitch) {

  @Override
  public String toString() {
    return "HomePosition[" +
           "world=" + this.world + ", " +
           "x=" + this.x + ", " +
           "y=" + this.y + ", " +
           "z=" + this.z + ", " +
           "yaw=" + this.yaw + ", " +
           "pitch=" + this.pitch + ']';
  }

  public static class Positions {

    public static HomePosition fromLocation(Location location) {
      return new HomePosition(
        location.getWorld()
          .getName(),
        location.getX(),
        location.getY(),
        location.getZ(),
        location.getYaw(),
        location.getPitch()
      );
    }

    public static Location toLocation(HomePosition position) {
      return new Location(
        Bukkit.getWorld(position.world()),
        position.x(),
        position.y(),
        position.z(),
        position.yaw(),
        position.pitch()
      );
    }
  }
}
