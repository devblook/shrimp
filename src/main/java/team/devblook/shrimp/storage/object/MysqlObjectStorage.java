package team.devblook.shrimp.storage.object;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import team.devblook.shrimp.model.HomeModel;
import team.devblook.shrimp.model.HomePosition;
import team.devblook.shrimp.model.UserModel;
import team.devblook.shrimp.storage.ObjectStorage;
import team.devblook.shrimp.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MysqlObjectStorage implements ObjectStorage<UserModel> {

  private final Storage storage;
  private final ComponentLogger logger;

  public MysqlObjectStorage(final Storage storage, final ComponentLogger logger) {
    this.storage = storage;
    this.logger = logger;
  }

  @Override
  public UserModel findSync(final String uuid) {
    final String query = """
                         SELECT SU.USER_ID, SU.USER_NAME, SH.HOME_ID, SH.HOME_NAME, SH.HOME_POSITION, SH.CREATED_AT
                         FROM SHRIMP_USERS AS SU
                                  INNER JOIN SHRIMP.SHRIMP_HOMES_USERS SHU ON SU.USER_ID = SHU.USER_ID
                                  INNER JOIN SHRIMP.SHRIMP_HOMES SH ON SHU.HOME_ID = SH.HOME_ID
                         WHERE SU.USER_ID = ?;
                         """;

    try (
      final Connection connection = this.storage.getConnection()
                                      .orElseThrow(() -> new RuntimeException("Connection is null"))
    ) {
      try (final PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, uuid);
        try (final ResultSet resultSet = statement.executeQuery()) {
          String userId = null;
          String userName = null;
          final Set<HomeModel> homes = new HashSet<>();
          while (resultSet.next()) {
            userId = resultSet.getString("USER_ID");
            userName = resultSet.getString("USER_NAME");
            final String homeId = resultSet.getString("HOME_ID");
            final String homeName = resultSet.getString("HOME_NAME");
            final HomePosition homePosition = HomePosition.fromString(resultSet.getString("HOME_POSITION"));
            final Instant createdAt = resultSet.getTimestamp("CREATED_AT")
                                        .toInstant();
            final HomeModel home = new HomeModel(homeId, homeName, homePosition, createdAt);
            homes.add(home);
          }

          if (userId == null) {
            return null;
          }

          final UserModel user = new UserModel(userId);
          user.setName(userName);
          homes.forEach(user::addHome);

          return user;
        }
      }
    } catch (SQLException e) {
      this.logger.error("Error while executing query", e);
    }

    return null;
  }

  @Override
  public UserModel findAsync(final String uuid) {
    return CompletableFuture.supplyAsync(() -> this.findSync(uuid))
             .exceptionally(e -> {
               this.logger.error("Error while executing query", e);
               return null;
             })
             .join();
  }

  @Override
  public Optional<UserModel> findOptionalSync(final String uuid) {
    return Optional.empty();
  }

  @Override
  public Optional<UserModel> findOptionalAsync(final String uuid) {
    return Optional.empty();
  }

  @Override
  public void saveSync(final UserModel object) {
    final String userQuery = """
                             INSERT INTO SHRIMP_USERS (USER_ID, USER_NAME)
                             VALUES (?, ?)
                             ON DUPLICATE KEY UPDATE USER_NAME = ?
                             """;
    final String homeQuery = """
                              INSERT INTO SHRIMP_HOMES (HOME_ID, HOME_NAME, HOME_POSITION, CREATED_AT)
                              VALUES (?, ?, ?, ?)
                              ON DUPLICATE KEY UPDATE HOME_NAME = ? AND HOME_POSITION = ?
                             """;
    final String relationQuery = """
                                  INSERT INTO SHRIMP_HOMES_USERS (HOME_ID, USER_ID)
                                  VALUES (?, ?)
                                 """;

    try (
      final Connection connection = this.storage.getConnection()
                                      .orElseThrow(() -> new RuntimeException("Connection is null"))
    ) {
      final PreparedStatement userStatement = connection.prepareStatement(userQuery);
      userStatement.setString(1, object.getId());
      userStatement.setString(2, object.getName());
      userStatement.setString(3, object.getName());
      userStatement.executeUpdate();

      object.getHomes()
        .forEach(home -> {
          try {
            final PreparedStatement homeStatement = connection.prepareStatement(homeQuery);
            homeStatement.setString(1, home.getId());
            homeStatement.setString(2, home.getName());
            homeStatement.setString(3, home.getPosition()
                                         .toString());
            homeStatement.setTimestamp(4, Timestamp.from(home.getCreatedAt()));
            homeStatement.setString(5, home.getName());
            homeStatement.setString(6, home.getPosition()
                                         .toString());

            final PreparedStatement relationStatement = connection.prepareStatement(relationQuery);
            relationStatement.setString(1, home.getId());
            relationStatement.setString(2, object.getId());

            homeStatement.executeUpdate();
            relationStatement.executeUpdate();
          } catch (SQLException e) {
            this.logger.error("Error while executing query", e);
          }
        });
    } catch (SQLException e) {
      this.logger.error("Error while executing query", e);
    }
  }

  @Override
  public void saveAsync(final UserModel object) {
    CompletableFuture.runAsync(() -> this.saveSync(object))
      .exceptionally(e -> {
        this.logger.error("Error while executing query", e);
        return null;
      });
  }

  @Override
  public void deleteSync(final String uuid) {
    final String relationQuery = """
                                 DELETE FROM SHRIMP_HOMES_USERS
                                 WHERE USER_ID = ?
                                 """;

    final String homeQuery = """
                             DELETE FROM SHRIMP_HOMES
                             WHERE HOME_ID = ?
                             """;

    final String userQuery = """
                             DELETE FROM SHRIMP_USERS
                             WHERE USER_ID = ?
                             """;

    try (
      final Connection connection = this.storage.getConnection()
                                      .orElseThrow(() -> new RuntimeException("Connection is null"))
    ) {
      final Statement statement = connection.createStatement();
      statement.addBatch(relationQuery);
      statement.addBatch(homeQuery);
      statement.addBatch(userQuery);
    } catch (SQLException e) {
      this.logger.error("Error while executing query", e);
    }
  }
}
