package team.devblook.shrimp.command.home;

import org.bukkit.entity.Player;
import team.devblook.shrimp.BukkitConfiguration;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.HomePosition;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.commandflow.annotated.CommandClass;
import team.unnamed.commandflow.annotated.annotation.Command;
import team.unnamed.commandflow.annotated.annotation.Sender;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Named;

@Command(names = "sethome")
public class SetHomeCommand implements CommandClass {

  @Inject
  private TypeRepository<UserModel> userModelRepository;

  @Inject
  @Named("messages")
  private BukkitConfiguration messagesFile;

  @Inject
  private BukkitConfiguration configurationFile;

  @Command(names = "")
  public void setHome(@Sender Player player, String homeName) {
    final UserModel userModel = userModelRepository.get(player.getUniqueId()
                                                          .toString());

    if (userModel == null) {
      player.sendMessage(this.messagesFile.getColoredComponent("error.user-not-found"));
      return;
    }

    int homeLimit = this.configurationFile.get()
                      .getInt("home-limit");

    if (homeLimit != 0) {
      if (userModel.getHomes()
            .size() >= homeLimit) {
        player.sendMessage(this.messagesFile.getColoredComponent("user.limit-homes"));
        return;
      }
    }

    final HomePosition homePosition = HomePosition.Positions.fromLocation(player.getLocation());
    final HomeModel home = new HomeModel(homeName, homeName, homePosition);
    boolean existHome = userModel.addHome(home);
    if (!existHome) {
      player.sendMessage(this.messagesFile.getColoredComponent("user.home-exist", "<home>", homeName));
      return;
    }
    player.sendMessage(this.messagesFile.getColoredComponent("user.create-home", "<home>", homeName));
  }
}
