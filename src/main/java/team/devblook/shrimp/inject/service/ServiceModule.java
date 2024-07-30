package team.devblook.shrimp.inject.service;

import team.devblook.shrimp.service.CommandService;
import team.devblook.shrimp.service.ListenerService;
import team.devblook.shrimp.service.Service;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {
    this.multibind(Service.class)
      .asSet()
      .to(CommandService.class)
      .to(ListenerService.class)
      .singleton();
  }
}
