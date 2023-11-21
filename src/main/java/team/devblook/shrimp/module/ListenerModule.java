package team.devblook.shrimp.module;

import org.bukkit.event.Listener;
import team.devblook.shrimp.listener.UserRegistryListener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

  @Override
  protected void configure() {
    multibind(Listener.class)
            .asSet()
            .to(UserRegistryListener.class);
  }
}
