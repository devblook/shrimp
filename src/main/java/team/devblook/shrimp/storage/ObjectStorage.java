package team.devblook.shrimp.storage;

import team.devblook.shrimp.model.Model;

import java.util.Optional;

public interface ObjectStorage<T extends Model> {

  T findSync(String uuid, T object);

  T findAsync(String uuid, T object);

  Optional<T> findOptionalSync(String uuid, T object);

  Optional<T> findOptionalAsync(String uuid, T object);

  void saveSync(T object);

  void saveAsync(T object);

  void deleteSync(String uuid);
}
