package team.devblook.shrimp.repository;

import team.devblook.shrimp.model.Model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TypeObjectRepository<T extends Model> implements TypeRepository<T> {

  private final Map<String, T> typeMap = new ConcurrentHashMap<>();

  public void add(T type) {
    this.typeMap.put(type.getId(), type);
  }

  public void remove(T type) {
    this.typeMap.remove(type.getId());
  }

  public T get(String id) {
    return this.typeMap.get(id);
  }

  public Map<String, T> getAll() {
    return this.typeMap;
  }

  public void clear() {
    this.typeMap.clear();
  }

  public boolean contains(String id) {
    return this.typeMap.containsKey(id);
  }

  public boolean isEmpty() {
    return this.typeMap.isEmpty();
  }
}
