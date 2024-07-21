package team.devblook.shrimp.storage;

import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface Storage {

  void configure();

  void checkConnection();

  default Optional<Connection> getConnection() throws SQLException {
    return Optional.empty();
  }

  default MongoDatabase getMongoDatabase() {
    return null;
  }

  default File getFolder() {
    return null;
  }
}
