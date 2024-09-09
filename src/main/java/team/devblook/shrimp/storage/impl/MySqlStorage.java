package team.devblook.shrimp.storage.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.file.FileConfiguration;
import team.devblook.shrimp.BukkitConfiguration;
import team.devblook.shrimp.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class MySqlStorage implements Storage {

  private HikariDataSource dataSource;
  private final ComponentLogger logger;
  private final BukkitConfiguration bukkitConfiguration;

  public MySqlStorage(final ComponentLogger logger, final BukkitConfiguration bukkitConfiguration) {
    this.logger = logger;
    this.bukkitConfiguration = bukkitConfiguration;
  }

  @Override
  public void configure() {
    final FileConfiguration config = this.bukkitConfiguration.get();
    final HikariConfig hikariConfig = new HikariConfig();
    final String uri = String.format("jdbc:mysql://%s:%d/%s",
                                     config.getString("MYSQL.host", "localhost"),
                                     config.getInt("MYSQL.port", 3306),
                                     config.getString("MYSQL.database", "shrimp-plugin"));

    hikariConfig.setJdbcUrl(uri);
    hikariConfig.setUsername(config.getString("MYSQL.username", "root"));
    hikariConfig.setPassword(config.getString("MYSQL.password", ""));
    hikariConfig.setMaximumPoolSize(10);
    this.dataSource = new HikariDataSource(hikariConfig);
    this.logger.info("MySQL connected successfully");
    this.checkConnection();
    if (!this.createTable()) {
      throw new RuntimeException("Error while creating table");
    }
  }

  @Override
  public void checkConnection() {
    try (final Connection connection = this.dataSource.getConnection()) {
      if (connection == null) {
        throw new RuntimeException("Connection is null");
      }

      final PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM DUAL");
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        throw new RuntimeException("Error while executing query");
      }

      resultSet.close();
    } catch (SQLException e) {
      throw new RuntimeException("Error while connecting to MySQL", e);
    }
    this.logger.info("MySQL connection is OK");
  }

  @Override
  public Optional<Connection> getConnection() throws SQLException {
    final Connection connection = this.dataSource.getConnection();
    if (connection == null) {
      return Optional.empty();
    } else {
      return Optional.of(connection);
    }
  }

  private boolean createTable() {
    try (final Connection connection = this.dataSource.getConnection()) {
      String homeTable = """
                         CREATE TABLE IF NOT EXISTS SHRIMP_HOMES
                         (
                           HOME_ID       VARCHAR(36) PRIMARY KEY,
                           HOME_NAME     VARCHAR(255) NOT NULL,
                           HOME_POSITION VARCHAR(255) NOT NULL,
                           CREATED_AT    TIMESTAMP    NOT NULL
                         )""";
      String userTable = """
                         CREATE TABLE IF NOT EXISTS SHRIMP_USERS
                         (
                           USER_ID   VARCHAR(36) PRIMARY KEY,
                           USER_NAME VARCHAR(255) NOT NULL
                         )""";
      String relationTable = """
                             CREATE TABLE IF NOT EXISTS SHRIMP_HOMES_USERS
                             (
                               HOME_ID VARCHAR(36) NOT NULL,
                               USER_ID VARCHAR(36) NOT NULL,
                               PRIMARY KEY (HOME_ID, USER_ID),
                               FOREIGN KEY (HOME_ID) REFERENCES SHRIMP_HOMES (HOME_ID),
                               FOREIGN KEY (USER_ID) REFERENCES SHRIMP_USERS (USER_ID)
                             )""";

      final Statement tablesStatement = connection.createStatement();
      tablesStatement.addBatch(homeTable);
      tablesStatement.addBatch(userTable);
      tablesStatement.addBatch(relationTable);
      tablesStatement.executeBatch();
      return true;
    } catch (SQLException e) {
      this.logger.error("Error while creating table", e);
      return false;
    }
  }
}
