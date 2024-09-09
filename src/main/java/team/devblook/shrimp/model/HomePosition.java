package team.devblook.shrimp.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public record HomePosition(String world, double x, double y, double z, float yaw, float pitch) {

  @Override
  public String toString() {
    return String.join(",",
                       this.world,
                       Double.toString(this.x),
                       Double.toString(this.y),
                       Double.toString(this.z),
                       Float.toString(this.yaw),
                       Float.toString(this.pitch)
    );
  }

  public static HomePosition fromString(String string) {
    String[] parts = string.split(",");
    return new HomePosition(
      parts[0],
      Double.parseDouble(parts[1]),
      Double.parseDouble(parts[2]),
      Double.parseDouble(parts[3]),
      Float.parseFloat(parts[4]),
      Float.parseFloat(parts[5])
    );
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
