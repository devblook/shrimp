package team.devblook.shrimp.storage.impl;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.Plugin;
import team.devblook.shrimp.storage.Storage;

import java.io.File;

public class LocalStorage implements Storage {

  private final String path;
  private File folder;
  private ComponentLogger logger;

  public LocalStorage(ComponentLogger logger, Plugin plugin) {
    this.logger = logger;
    this.path = plugin.getDataFolder() + "/users";
  }

  @Override
  public void configure() {
    this.folder = new File(this.path);

    if (!this.folder.exists() && !this.folder.mkdirs()) {
      throw new RuntimeException("Failed to create folder");
    }
  }

  @Override
  public void checkConnection() {
    final File[] files = this.folder.listFiles();

    if (files == null) {
      throw new RuntimeException("Failed to list files");
    }

    this.logger.info("Checking local storage, any files outside the required ecosystem will be deleted.");

    for (File f : files) {
      if (f.isDirectory()) {
        this.logger.info("A directory called {} was found, it will be deleted", f.getName());
        if (!f.delete()) {
          this.logger.info("Failed to delete directory {}", f.getName());
        }
        continue;
      }

      if (!f.getName()
             .endsWith(".json")) {
        this.logger.info("A file called {} was found, it will be deleted", f.getName());

        if (!f.delete()) {
          this.logger.info("Failed to delete file {}", f.getName());
        }
      }
    }
    this.logger.info("Local storage is OK");
  }

  @Override
  public File getFolder() {
    return this.folder;
  }
}
