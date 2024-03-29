package team.devblook.shrimp.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public final class HomePosition {
  private final String world;
  private final double x;
  private final double y;
  private final double z;
  private final float yaw;
  private final float pitch;

  public HomePosition(String world, double x, double y, double z, float yaw, float pitch) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public String world() {
    return this.world;
  }

  public double x() {
    return this.x;
  }

  public double y() {
    return this.y;
  }

  public double z() {
    return this.z;
  }

  public float yaw() {
    return this.yaw;
  }

  public float pitch() {
    return this.pitch;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (HomePosition) obj;
    return Objects.equals(this.world, that.world) &&
            Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
            Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y) &&
            Double.doubleToLongBits(this.z) == Double.doubleToLongBits(that.z) &&
            Float.floatToIntBits(this.yaw) == Float.floatToIntBits(that.yaw) &&
            Float.floatToIntBits(this.pitch) == Float.floatToIntBits(that.pitch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
  }

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
              location.getWorld().getName(),
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
