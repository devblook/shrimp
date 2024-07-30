package team.devblook.shrimp;

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
}
