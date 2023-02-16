package team.devblook.shrimp.storage;

import team.devblook.shrimp.user.User;


public interface Storage {
    default void connect() {
    }

    void save(User user);

    User find(String id);

}
