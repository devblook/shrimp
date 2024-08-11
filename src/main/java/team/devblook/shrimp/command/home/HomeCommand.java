package team.devblook.shrimp.command.home;

import org.bukkit.entity.Player;
import team.devblook.shrimp.BukkitConfiguration;
import team.devblook.shrimp.manager.GuiManager;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.HomePosition;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;
import team.unnamed.commandflow.annotated.annotation.Sender;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Named;

@Command(names = "home")
public class HomeCommand implements CommandClass {

  @Inject
  @Named("messages")
  private BukkitConfiguration messagesFile;

  @Inject
  private GuiManager guiManager;

  @Command(names = "")
  public void home(@Sender Player sender, HomeModel home) {
    if (home == null) {
      this.guiManager.openGui(sender);
      return;
    }

    sender.teleportAsync(HomePosition.Positions.toLocation(home.getPosition()))
      .exceptionally(throwable -> {
        sender.sendMessage(this.messagesFile.getColoredComponent("user.teleport-failed"));
        return null;
      });
    sender.sendMessage(this.messagesFile.getColoredComponent("user.teleport-to-home", "<home>", home.getName()));
  }
}
