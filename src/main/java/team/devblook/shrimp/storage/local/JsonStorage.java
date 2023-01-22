package team.devblook.shrimp.storage.local;

import com.google.gson.Gson;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.user.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class JsonStorage implements Storage {

    private static final Gson GSON = new Gson();
    private static final Executor EXECUTOR = Executors.newCachedThreadPool();
    private File folder;

    @Inject
    public JsonStorage(Shrimp plugin) {
        this.folder = new File(plugin.getDataFolder(), "users");
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Could not create folder " + folder.getAbsolutePath());
        }
    }

    @Override
    public void save(User user) {
        CompletableFuture.runAsync(() -> {
            File file = new File(folder, user.id() + ".json");

            try (Writer writer = new BufferedWriter(new FileWriter(file))) {
                if (!file.exists() && !file.createNewFile()) {
                    throw new IOException("Could not create file " + file.getAbsolutePath());
                }
                GSON.toJson(user, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, EXECUTOR);
    }

    @Override
    public User find(String id) {
        return CompletableFuture.supplyAsync(() -> {
            File file = new File(folder, id + ".json");

            try (Reader reader = new BufferedReader(new FileReader(file))) {
                return GSON.fromJson(reader, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }, EXECUTOR).join();
    }
}
