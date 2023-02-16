package team.devblook.shrimp.storage.cloud;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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

    @Inject
    public MongoStorage(BukkitConfiguration settings, Shrimp plugin) {
        this.plugin = plugin;
        this.collection = settings.get().getString("MONGODB.collection");
        this.database = settings.get().getString("MONGODB.database");
        this.user = settings.get().getString("MONGODB.user");
        this.host = settings.get().getString("MONGODB.host");
        this.port = settings.get().getString("MONGODB.port");
        this.password = settings.get().getString("MONGODB.password");
    }

    @Override
    public void connect() {
        CompletableFuture.runAsync(() -> {
            try {
                this.mongoClient = MongoClients.create("mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database);
                plugin.getLogger().info("MongoDB connected");

            } catch (Exception e) {
                plugin.getLogger().info("MongoDB connection failed");
                e.printStackTrace();
            }

        }).join();
    }

    @Override
    public void save(User user) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.database);
        MongoCollection<Document> collection = mongoDatabase.getCollection(this.collection);
        collection.insertOne(new Document("id", user.id()).append("homes", user.homes()));

    }

    @Override
    public User find(String id) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.database);
        MongoCollection<Document> collection = mongoDatabase.getCollection(this.collection);
        Document document = collection.find(new Document("id", id)).first();
        return new User(id);
    }
}
