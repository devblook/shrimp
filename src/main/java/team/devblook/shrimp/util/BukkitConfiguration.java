package team.devblook.shrimp.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class BukkitConfiguration {

  private final File file;
  private FileConfiguration config;

  public BukkitConfiguration(File folder, String fileName) {
    if (!folder.exists() && !folder.mkdirs()) {
      throw new IllegalStateException("Plugin folder" + folder.getName() + "cannot be created");
    }

    this.file = new File(folder, fileName + ".yml");

    if (!this.file.exists()) {
      try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(this.file.getName())) {
        if (stream != null) {
          Files.copy(stream, this.file.toPath());
        }
      } catch (IOException e) {
        throw new UncheckedIOException("An error occurred while loading file '" + fileName + "'.",
                e);
      }
    }
    this.reload();
  }

  public BukkitConfiguration(Plugin plugin, String fileName) {
    this(plugin.getDataFolder(), fileName);
  }

  public FileConfiguration get() {
    return this.config;
  }

  public void reload() {
    this.config = YamlConfiguration.loadConfiguration(this.file);
  }

  public void save() {
    try {
      this.config.save(this.file);
    } catch (IOException e) {
      throw new UncheckedIOException(
              "An error occurred while saving file '" + this.file.getName() + "'.", e);
    }
  }

  public Component getMessage(String path) {
    String message = this.config.getString(path);
    return MiniMessage.miniMessage().deserialize(message);
  }
}
