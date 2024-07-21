package team.devblook.shrimp.storage.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import team.devblook.shrimp.storage.Storage;

import java.util.ArrayList;

public class MongoStorage implements Storage {

  private MongoClient client;
  private MongoDatabase database;
  private final String uri;
  private final String nameDatabase;
  private final ComponentLogger logger;

  public MongoStorage(ComponentLogger logger, String uri, String nameDatabase) {
    this.logger = logger;
    this.uri = uri;
    this.nameDatabase = nameDatabase;
  }

  @Override
  public void configure() {
    this.client = MongoClients.create(this.uri);

    this.database = this.client.getDatabase(this.nameDatabase);

    boolean collectionExists = this.database.listCollectionNames()
                                 .into(new ArrayList<>())
                                 .contains("shrimp-plugin");
    if (!collectionExists) {
      database.createCollection("shrimp-plugin");
    }
    this.logger.info("MongoDB connected successfully");
  }

  @Override
  public void checkConnection() {
    if (this.client == null) {
      throw new RuntimeException("MongoClient is null");
    }

    if (this.database == null) {
      throw new RuntimeException("MongoDatabase is null");
    }

    if (this.client.listDatabaseNames()
          .into(new ArrayList<>())
          .contains(this.nameDatabase)) {
      throw new RuntimeException("Database not found");
    }

    if (this.database.listCollectionNames()
          .into(new ArrayList<>())
          .contains("shrimp-plugin")) {
      throw new RuntimeException("Collection not found");
    }

    this.logger.info("MongoDB connection is OK");
  }

  @Override
  public MongoDatabase getMongoDatabase() {
    return this.client.getDatabase(this.nameDatabase);
  }
}
