package team.devblook.shrimp.command.home;

import org.bukkit.entity.Player;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.HomePosition;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;
import team.unnamed.commandflow.annotated.annotation.Sender;

@Command(names = "home")
public class HomeCommand implements CommandClass {

  @Command(names = "")
  public void home(@Sender Player sender, HomeModel home) {
    if (home == null) {
      sender.sendMessage("Home not found");
      return;
    }

    sender.teleport(HomePosition.Positions.toLocation(home.getPosition()));
  }
}
