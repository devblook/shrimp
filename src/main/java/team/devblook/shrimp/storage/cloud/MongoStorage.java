package team.devblook.shrimp.storage.cloud;

import team.devblook.shrimp.storage.Storage;
import team.devblook.shrimp.user.User;

public class MongoStorage implements Storage {

    @Override
    public void connect() {
        Storage.super.connect();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User find(String id) {
        return null;
    }
}
