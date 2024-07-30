package team.devblook.shrimp.command.home;

import org.bukkit.entity.Player;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.HomePosition;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;
import team.unnamed.commandflow.annotated.annotation.Sender;
import team.unnamed.inject.Inject;

@Command(names = "sethome")
public class SetHomeCommand implements CommandClass {

  @Inject
  private TypeRepository<UserModel> userModelRepository;

  @Command(names = "")
  public void setHome(@Sender Player player, String homeName) {
    final UserModel userModel = userModelRepository.get(player.getUniqueId()
                                                          .toString());

    if (userModel == null) {
      player.sendMessage("User not found");
      return;
    }

    final HomePosition homePosition = HomePosition.Positions.fromLocation(player.getLocation());
    final HomeModel home = new HomeModel(homeName, homeName, homePosition);
    userModel.addHome(home);
    player.sendMessage("Home set" );
  }
}
