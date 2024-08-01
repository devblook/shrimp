package team.devblook.shrimp.storage;

import team.devblook.shrimp.model.Model;

import java.util.Optional;

public interface ObjectStorage<T extends Model> {

  T findSync(String uuid);

  T findAsync(String uuid);

  Optional<T> findOptionalSync(String uuid);

  Optional<T> findOptionalAsync(String uuid);

  void saveSync(T object);

  void saveAsync(T object);

  void deleteSync(String uuid);
}
