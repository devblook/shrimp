package team.devblook.shrimp.manager;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import team.devblook.shrimp.BukkitConfiguration;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.HomePosition;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.repository.TypeRepository;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Named;
import team.unnamed.inject.Singleton;

import java.util.Set;

@Singleton
public class GuiManager {

  @Inject
  private TypeRepository<UserModel> userModelRepository;

  @Inject
  @Named("messages")
  private BukkitConfiguration messagesFile;

  public void openGui(final Player player) {
    final String id = player.getUniqueId()
                        .toString();

    final UserModel userModel = userModelRepository.get(id);

    if (userModel == null) {
      player.sendMessage(this.messagesFile.getColoredComponent("error.user-not-found"));
      return;
    }

    final Gui gui = Gui.gui()
                      .title(this.messagesFile.getColoredComponent("gui.title"))
                      .type(GuiType.CHEST)
                      .rows(5)
                      .create();

    gui.setDefaultClickAction(event -> event.setCancelled(true));

    final Set<HomeModel> homes = userModel.getHomes();
    homes.forEach(homeElement -> {
      GuiItem guiItem = ItemBuilder.from(Material.GREEN_BED)
                          .name(this.messagesFile.getColoredComponent("gui.item.name", "<home>", homeElement.getName()))
                          .lore(this.messagesFile.getColoredComponents(
                            "gui.item.lore",
                            "<home>",
                            homeElement.getName(),
                            "<world>",
                            homeElement.getPosition()
                              .world(),
                            "<x>",
                            String.valueOf(homeElement.getPosition()
                                             .x()),
                            "<y>",
                            String.valueOf(homeElement.getPosition()
                                             .y()),
                            "<z>",
                            String.valueOf(homeElement.getPosition()
                                             .z())
                          ))
                          .asGuiItem(inventoryClickEvent -> {
                            final ClickType clickType = inventoryClickEvent.getClick();

                            switch (clickType) {
                              case LEFT:
                                player.teleportAsync(
                                  HomePosition.Positions.toLocation(homeElement.getPosition())
                                );
                                player.sendMessage(this.messagesFile.getColoredComponent(
                                  "user.teleport-to-home",
                                  "<home>",
                                  homeElement.getName()));
                                break;
                              case RIGHT:
                                userModel.removeHome(homeElement);
                                player.sendMessage(this.messagesFile.getColoredComponent(
                                  "user.delete-home",
                                  "<home>",
                                  homeElement.getName()));
                                openGui(player);
                                break;
                              default:
                                break;
                            }
                          });

      gui.addItem(guiItem);
    });

    gui.open(player);
  }
}
