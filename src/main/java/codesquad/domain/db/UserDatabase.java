package codesquad.domain.db;

import codesquad.domain.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    Map<String, User> users = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: users.keySet()) {
            stringBuilder.append(key).append(": ").append(users.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }

    public void appendUser(User user) { users.put(user.userId(), user); }
    public User getUserById(String id) { return users.get(id); }
    public Map<String, User> getUsers() { return users; }
}
