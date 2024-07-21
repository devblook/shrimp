package team.devblook.shrimp.storage.object;

import team.devblook.shrimp.model.Model;
import team.devblook.shrimp.storage.ObjectStorage;

import java.util.Optional;

public class MongoObjectStorage <T extends Model> implements ObjectStorage<T> {
  @Override
  public T findSync(final String uuid, final T object) {
    return null;
  }

  @Override
  public T findAsync(final String uuid, final T object) {
    return null;
  }

  @Override
  public Optional<T> findOptionalSync(final String uuid, final T object) {
    return Optional.empty();
  }

  @Override
  public Optional<T> findOptionalAsync(final String uuid, final T object) {
    return Optional.empty();
  }

  @Override
  public void saveSync(final T object) {

  }

  @Override
  public void saveAsync(final T object) {

  }

  @Override
  public void deleteSync(final String uuid) {

  }
}
