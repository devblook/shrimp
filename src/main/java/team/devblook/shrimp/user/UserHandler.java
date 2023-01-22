package team.devblook.shrimp.user;

import team.devblook.shrimp.storage.Storage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class UserHandler {

    @Inject
    private Map<String, User> users;
    @Inject
    private Storage storage;

    public void registry(String id) {
        User user = storage.find(id);

        if (user == null) {
            user = new User(id);
        }

        users.put(user.id(), user);
        storage.save(user);
    }

    public void unRegistry(String id) {
        User user = users.get(id);
        storage.save(user);
        users.remove(id);
    }

    public User get(String id) {
        return users.get(id);
    }

    public void remove(String id) {
        users.remove(id);
    }

    public void update(User user) {
        users.put(user.id(), user);
        storage.save(user);
    }

    public void saveAll() {
        users.forEach((id, user) -> storage.save(user));
    }

    public void clear() {
        users.clear();
    }
}
