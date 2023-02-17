package team.devblook.shrimp.storage.cloud;

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

    @Inject
    public MySQLStorage(BukkitConfiguration settings, Shrimp plugin) {
        this.plugin = plugin;
        this.table = settings.get().getString("MYSQL.table");
        this.database = settings.get().getString("MYSQL.database");
        this.user = settings.get().getString("MYSQL.user");
        this.host = settings.get().getString("MYSQL.host");
        this.port = settings.get().getInt("MYSQL.port");
        this.password = settings.get().getString("MYSQL.password");
    }
    @Override
    public void connect() {
        CompletableFuture.runAsync(() -> {
            try {
                String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
                connection = DriverManager.getConnection(jdbcUrl, user, password);
                plugin.getComponentLogger().info(Component.text("MySQL connected").color(NamedTextColor.GREEN));
            } catch (SQLException e) {
                plugin.getComponentLogger().info(Component.text("MySQL connection failed").color(NamedTextColor.RED));
                e.printStackTrace();
            }
        }).join();
    }

    @Override
    public void save(User user) {
        try {
            String sql = "INSERT INTO " + table + " (id, homes) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.id());
            statement.setObject(2, user.homes());
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
                return new User(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(id);
    }
}
