package team.devblook.shrimp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class HomeModel implements Model {

  private final String id;
  private String name;
  private HomePosition position;
  private Instant createdAt;

  public HomeModel(final String id) {
    this.id = id;
  }

  public HomeModel(final String id, final String name, final HomePosition position) {
    this.id = id;
    this.name = name;
    this.createdAt = Instant.now();
    this.position = position;
  }

  @Override
  public String getId() {
    return this.id;
  }
}
