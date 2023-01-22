package team.devblook.shrimp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.devblook.shrimp.user.UserHandler;

import javax.inject.Inject;

public class UserRegistryListener implements Listener {

    @Inject
    private UserHandler handler;

    @EventHandler
    private void onUserRegistry(PlayerJoinEvent event) {
        handler.registry(event.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    private void onUserUnRegistry(PlayerQuitEvent event) {
        handler.unRegistry(event.getPlayer().getUniqueId().toString());
    }


}
