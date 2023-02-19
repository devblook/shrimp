package team.devblook.shrimp.storage.cloud;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                this.mongoClient = MongoClients.create("mongodb+srv://" + user + ":" + password + "@" + host);
                plugin.getComponentLogger().info(Component.text("MongoDB connected").color(NamedTextColor.GREEN));

            } catch (Exception e) {
                plugin.getComponentLogger().info(Component.text("MongoDB connection failed").color(NamedTextColor.RED));
                e.printStackTrace();
            }

        }).join();
    }

    @Override
    public void save(User user) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.database);
        MongoCollection<Document> collection = mongoDatabase.getCollection(this.collection);
        collection.insertOne(Document.parse(new Gson().toJson(user)));
    }

    @Override
    public User find(String id) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(this.database);
        MongoCollection<Document> collection = mongoDatabase.getCollection(this.collection);
        Document document = collection.find(new Document("id", id)).first();
        if (document != null) {
            return new Gson().fromJson(document.toJson(), User.class);
        } else {
            return null;
        }
    }
}
