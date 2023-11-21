package team.devblook.shrimp.user;

import team.devblook.shrimp.storage.Storage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class UserHandler {

  @Inject
  private Map<String, User> users;
  @Inject
  private Storage storage;

  public void registry(String id) {
    User user = this.storage.find(id);

    if (user == null) {
      user = new User(id);

    }

    this.users.put(user.id(), user);
    this.storage.save(user);
  }

  public void unRegistry(String id) {
    User user = this.users.get(id);
    this.storage.save(user);
    this.users.remove(id);
  }

  public User get(String id) {
    return this.users.get(id);
  }

  public void remove(String id) {
    this.users.remove(id);
  }

  public void update(User user) {
    this.users.put(user.id(), user);
    this.storage.save(user);
  }

  public void saveAll() {
    this.users.forEach((id, user) -> this.storage.save(user));
  }

  public void clear() {
    this.users.clear();
  }
}
