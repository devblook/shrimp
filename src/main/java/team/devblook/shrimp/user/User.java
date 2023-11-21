package team.devblook.shrimp.user;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import team.devblook.shrimp.home.Home;
import team.devblook.shrimp.home.HomePosition;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User {

  private final String id;
  private final Map<String, Home> homes;

  public User(String id) {
    this.id = id;
    this.homes = new ConcurrentHashMap<>();
  }

  public String id() {
    return this.id;
  }

  public void addHome(String name, Location location) {
    this.homes.put(name, new Home(
            name,
            HomePosition.Positions.fromLocation(location)
    ));
  }

  public boolean hasHome(String name) {
    return this.homes.containsKey(name);
  }

  public int sizeHomes() {
    return this.homes.size();
  }

  public void removeHome(String name) {
    this.homes.remove(name);
  }

  @Nullable
  public Home home(String name) {
    return this.homes.get(name) == null ? null : this.homes.get(name);
  }

  public Collection<Home> homes() {
    return this.homes.values();
  }
}
