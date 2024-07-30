package team.devblook.shrimp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.inject.Inject;

public class UserRegistryListener implements Listener {

  @Inject
  private TypeRepository<UserModel> userModelRepository;

  @EventHandler
  public void onUserRegistry(final PlayerJoinEvent event) {
    final Player player = event.getPlayer();

    if (userModelRepository.get(player.getUniqueId()
                                  .toString()) != null) {
      return;
    }

    final UserModel userModel = new UserModel(player.getUniqueId()
                                                .toString());
    userModelRepository.add(userModel);
  }
}
