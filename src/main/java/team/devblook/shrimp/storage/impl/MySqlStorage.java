package team.devblook.shrimp.storage.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import team.devblook.shrimp.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySqlStorage implements Storage {

  private HikariDataSource dataSource;
  private final ComponentLogger logger;

  public MySqlStorage(ComponentLogger logger) {
    this.logger = logger;
  }

  @Override
  public void configure() {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3306/shrimp-plugin");
    config.setUsername("root");
    config.setPassword("root");
    config.setMaximumPoolSize(10);
    this.dataSource = new HikariDataSource(config);
    this.logger.info("MySQL connected successfully");
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
}
