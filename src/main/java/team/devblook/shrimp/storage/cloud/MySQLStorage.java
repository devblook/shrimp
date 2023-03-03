package team.devblook.shrimp.storage.cloud;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import team.devblook.shrimp.Shrimp;
import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.user.User;
import team.devblook.shrimp.util.BukkitConfiguration;

import javax.inject.Inject;
import java.sql.*;
import java.util.concurrent.CompletableFuture;

public class MySQLStorage implements Storage {
    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String database;
    private final String table;
    private Connection connection;
    private final Shrimp plugin;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @Inject
    public MySQLStorage(BukkitConfiguration settings, Shrimp plugin) {
        this.plugin = plugin;
        this.table = settings.get().getString("MYSQL.table-prefix");
        this.database = settings.get().getString("MYSQL.database");
        this.user = settings.get().getString("MYSQL.username");
        this.host = settings.get().getString("MYSQL.host");
        this.port = settings.get().getInt("MYSQL.port");
        this.password = settings.get().getString("MYSQL.password");
    }

    @Override
    public void connect() {
        CompletableFuture.runAsync(() -> {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false", user, password);

                statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (id VARCHAR(36), homes VARCHAR(1000))");
                plugin.getComponentLogger().info(Component.text("MySQL connected").color(NamedTextColor.GREEN));
            } catch (Exception e) {
                plugin.getComponentLogger().info(Component.text("MySQL connection failed").color(NamedTextColor.RED));
                e.printStackTrace();
            }
        }).join();
    }

    @Override
    public void save(User user) {
        try {
            String sql = "INSERT INTO " + table + " (id, homes) VALUES (?, ?) ON DUPLICATE KEY UPDATE homes=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.id());
            Gson gson = new Gson();
            String homesJson = gson.toJson(user.homes());
            statement.setObject(2, homesJson);
            statement.setObject(3, homesJson);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String id) {
        try {
            String sql = "SELECT homes FROM " + table + " WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Object homes = resultSet.getObject("homes");
                return new User(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(id);
    }
}
