package team.devblook.shrimp.command.module;

import team.devblook.shrimp.command.part.HomeListPart;
import team.devblook.shrimp.model.HomeModel;
import team.unnamed.commandflow.annotated.part.AbstractModule;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Singleton;

@Singleton
public class HomePartModule extends AbstractModule {

  @Inject
  private HomeListPart homeListPart;

  @Override
  public void configure() {
    this.bindFactory(HomeModel.class, homeListPart);
  }
}
