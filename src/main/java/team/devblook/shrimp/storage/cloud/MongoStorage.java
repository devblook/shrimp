package team.devblook.shrimp.storage.cloud;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class MongoStorage implements Storage {
  private final String host;
  private final String port;
  private final String user;
  private final String password;
  private final String database;
  private final String collection;
  private MongoClient mongoClient;
  private final Shrimp plugin;
  private final Logger logger;
  private static final Gson gson = new Gson();

  @Inject
  public MongoStorage(BukkitConfiguration settings, Shrimp plugin) {
    this.plugin = plugin;
    this.logger = plugin.getSLF4JLogger();
    this.host = settings.get().getString("MONGODB.host");
    this.port = settings.get().getString("MONGODB.port");
    this.database = settings.get().getString("MONGODB.database");
    this.user = settings.get().getString("MONGODB.username");
    this.password = settings.get().getString("MONGODB.password");
    this.collection = settings.get().getString("MONGODB.collection");
  }

  @Override
  public void connect() {
    CompletableFuture.runAsync(() -> {
      this.mongoClient = MongoClients.create("mongodb+srv://" + this.user + ":" + this.password + "@" + this.host);
      this.logger.info("MongoDB connected");
    }).whenComplete((aVoid, throwable) -> {
      if (throwable != null) {
        this.logger.error("MongoDB connection failed", throwable);
      }
    });
  }

  @Override
  public void save(User user) {
    CompletableFuture.runAsync(() -> {
      MongoDatabase mongoDatabase = this.mongoClient.getDatabase(this.database);
      MongoCollection<Document> collection = mongoDatabase.getCollection(this.collection);
      if (collection.find(new Document("id", user.id())).first() != null) {
        collection.updateOne(new Document("id", user.id()), new Document("$set", Document.parse(gson.toJson(user))));
        return;
      }
      collection.insertOne(Document.parse(new Gson().toJson(user)));
    }).whenComplete((aVoid, throwable) -> {
      if (throwable != null) {
        this.logger.error("MongoDB save failed", throwable);
      }
    });

  }

  @Override
  public User find(String id) {
    MongoDatabase mongoDatabase = this.mongoClient.getDatabase(this.database);
    MongoCollection<Document> collection = mongoDatabase.getCollection(this.collection);
    Document document = collection.find(new Document("id", id)).first();
    if (document == null) {
      return null;
    }
    return gson.fromJson(document.toJson(), User.class);
  }

}
