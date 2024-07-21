package team.devblook.shrimp.repository;

import team.devblook.shrimp.model.Model;

import java.util.Map;

public interface TypeRepository<T extends Model> {

  void add(T type);

  void remove(T type);

  T get(String id);

  Map<String, T> getAll();

  void clear();

  boolean contains(String id);

  boolean isEmpty();
}
