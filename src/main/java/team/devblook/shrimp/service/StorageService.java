package team.devblook.shrimp.service;

import team.devblook.shrimp.manager.UserManager;
import team.devblook.shrimp.storage.Storage;
import team.unnamed.inject.Inject;

public class StorageService implements Service {

  @Inject
  private UserManager userManager;

  @Inject
  private Storage storage;

  @Override
  public void start() {
    this.storage.configure();
    this.storage.checkConnection();
  }

  @Override
  public void stop() {
    this.userManager.saveMany();
  }
}
