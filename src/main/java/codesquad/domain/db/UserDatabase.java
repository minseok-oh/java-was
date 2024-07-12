package codesquad.domain.db;

import codesquad.domain.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDatabase implements Database<String, User> {
    Map<String, User> userDB = new ConcurrentHashMap<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: userDB.keySet()) {
            stringBuilder.append(key).append(": ").append(userDB.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String append(User user) {
        String userId = user.userId();
        userDB.put(userId, user);
        return userId;
    }

    @Override
    public User getById(String id) {
        if (!userDB.containsKey(id)) return null;
        return userDB.get(id);
    }

    @Override
    public Map<String, User> getAll() { return userDB; }

    @Override
    public void deleteById(String id) {
        if (!userDB.containsKey(id)) return;
        userDB.remove(id);
    }
}
