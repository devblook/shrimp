package team.devblook.shrimp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

public class BukkitConfiguration {

  private final File file;
  private FileConfiguration configuration;

  public BukkitConfiguration(File folder, String fileName) {
    if (!folder.exists() && !folder.mkdirs()) {
      throw new RuntimeException("Could not create folder: " + folder);
    }

    this.file = new File(folder, fileName);

    if (!this.file.exists()) {
      try (
        InputStream stream = getClass().getClassLoader()
                               .getResourceAsStream(this.file.getName())
      ) {
        if (stream != null) {
          Files.copy(stream, this.file.toPath());
        }
      } catch (IOException e) {
        throw new UncheckedIOException("Could not copy file: " + fileName, e);
      }
    }
    reload();
  }

  public BukkitConfiguration(Plugin plugin, String fileName) {
    this(plugin.getDataFolder(), fileName);
  }

  public void reload() {
    this.configuration = YamlConfiguration.loadConfiguration(this.file);
  }

  public void save() {
    try {
      this.configuration.save(this.file);
    } catch (IOException e) {
      throw new UncheckedIOException("Could not save file: " + this.file, e);
    }
  }

  public FileConfiguration get() {
    return this.configuration;
  }

  public Component getComponent(String path) {
    return Component.text(this.get()
                            .getString(path, path));
  }

  public Component getColoredComponent(final String path) {
    final MiniMessage miniMessage = MiniMessage.miniMessage();
    return miniMessage.deserialize(this.get()
                                     .getString(path, path));
  }

  public Component getColoredComponent(String path, String... args) {
    final MiniMessage miniMessage = MiniMessage.miniMessage();

    if (args.length % 2 != 0) {
      throw new IllegalArgumentException("Arguments must be in pairs");
    }

    String message = this.get()
                       .getString(path, path);

    for (int i = 0; i < args.length; i += 2) {
      message = message.replace(args[i], args[i + 1]);
    }

    return miniMessage.deserialize(message)
             .decorationIfAbsent(
               TextDecoration.ITALIC,
               TextDecoration.State.FALSE);
  }

  public List<Component> getColoredComponents(String path) {
    final MiniMessage miniMessage = MiniMessage.miniMessage();
    List<String> strings = this.get()
                             .getStringList(path);

    if (strings.isEmpty()) {
      return Collections.emptyList();
    }

    return strings.stream()
             .map(element -> miniMessage.deserialize(element)
                               .decorationIfAbsent(
                                 TextDecoration.ITALIC,
                                 TextDecoration.State.FALSE))
             .toList();
  }

  public List<Component> getColoredComponents(String path, String... args) {
    final MiniMessage miniMessage = MiniMessage.miniMessage();

    if (args.length % 2 != 0) {
      throw new IllegalArgumentException("Arguments must be in pairs");
    }

    List<String> strings = this.get()
                             .getStringList(path);

    if (strings.isEmpty()) {
      return Collections.emptyList();
    }

    return strings.stream()
             .map(s -> {
               for (int i = 0; i < args.length; i += 2) {
                 s = s.replace(args[i], args[i + 1]);
               }
               return miniMessage.deserialize(s)
                        .decorationIfAbsent(
                          TextDecoration.ITALIC,
                          TextDecoration.State.FALSE);
             })
             .toList();
  }
}
