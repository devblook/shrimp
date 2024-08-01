package team.devblook.shrimp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.devblook.shrimp.manager.UserManager;
import team.devblook.shrimp.model.UserModel;
import team.unnamed.inject.Inject;

public class UserRegistryListener implements Listener {

  @Inject
  private UserManager userManager;

  @EventHandler
  public void onUserRegistry(final PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final UserModel userModel = userManager.findOne(player.getUniqueId());
    if (userModel == null) {
      throw new IllegalStateException("UserModel is null");
    }
  }

  @EventHandler
  public void onUserSave(final PlayerQuitEvent event) {
    final Player player = event.getPlayer();
    final UserModel userModel = userManager.findOne(player.getUniqueId());
    userManager.saveOne(userModel);
  }
}
