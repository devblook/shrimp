package team.devblook.shrimp.service;

import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;

import javax.inject.Inject;

public class StorageService implements Service {
  @Inject
  private Shrimp plugin;
  @Inject
  private Storage storage;

  @Override
  public void start() {
    this.plugin.getSLF4JLogger()
            .info("Starting Storage with " + this.storage.getClass().getSimpleName() + "...");
    this.storage.connect();
  }

  @Override
  public void stop() {
    this.plugin.getSLF4JLogger()
            .info("Stopping Storage with " + this.storage.getClass().getSimpleName() + "...");
  }
}
