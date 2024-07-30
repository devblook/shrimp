package team.devblook.shrimp.inject.repository;

import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeObjectRepository;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

public class RepositoryModule extends AbstractModule {

  @Override
  protected void configure() {
    this.bind(new TypeReference<TypeRepository<UserModel>>() { })
      .toInstance(new TypeObjectRepository<>());
  }
}
