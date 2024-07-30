package team.devblook.shrimp.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserModel implements Model {

  private final String id;
  private Set<HomeModel> homes;

  public UserModel(final String id) {
    this.id = id;
    this.homes = new HashSet<>();
  }

  @Override
  public String getId() {
    return this.id;
  }

  public void addHome(HomeModel home) {
    this.homes.add(home);
  }

  public void removeHome(HomeModel home) {
    this.homes.remove(home);
  }

  @Nullable
  public HomeModel getHome(String name) {
    return this.homes.stream()
             .filter(home -> home.getName()
                               .equalsIgnoreCase(name))
             .findFirst()
             .orElse(null);
  }
}
