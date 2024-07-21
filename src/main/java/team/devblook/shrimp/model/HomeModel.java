package team.devblook.shrimp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HomeModel implements Model {

  private final String id;
  private String name;
  private Date createdAt;

  public HomeModel(final String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }
}
