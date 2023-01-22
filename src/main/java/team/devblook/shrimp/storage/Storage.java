package team.devblook.shrimp.storage;

import team.devblook.shrimp.user.User;

public interface Storage {

    default void connect() {
        // connect to database
    }

    void save(User user);

    User find(String id);

}
