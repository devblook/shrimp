package team.devblook.shrimp.model;

import lombok.Getter;
import lombok.Setter;

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
}
