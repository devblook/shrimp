package team.devblook.shrimp.inject.listener;

import org.bukkit.event.Listener;
import team.devblook.shrimp.listener.UserRegistryListener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

  @Override
  protected void configure() {
    this.multibind(Listener.class)
      .asSet()
      .to(UserRegistryListener.class)
      .singleton();
  }
}
