package team.devblook.shrimp.manager;

import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.devblook.shrimp.storage.ObjectStorage;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Singleton;

import java.util.UUID;

@Singleton
public class UserManager {

  @Inject
  private TypeRepository<UserModel> userRepository;

  @Inject
  private ObjectStorage<UserModel> userStorage;

  public UserModel findOne(UUID uuid) {
    final String id = uuid.toString();
    if (userRepository.contains(id)) {
      return userRepository.get(id);
    }

    UserModel user = userStorage.findAsync(id);
    if (user != null) {
      userRepository.add(user);
      return user;
    }

    user = new UserModel(id);
    userRepository.add(user);
    return user;
  }

  public void saveOne(UserModel user) {
    userRepository.remove(user);
    userStorage.saveAsync(user);
  }

  public void saveMany() {
    this.userRepository.getAll()
      .forEach((__, user) -> this.saveOne(user));
  }
}
