package codesquad.domain;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    Map<String, User> users = new HashMap<>();

    public void appendUser(User user) { users.put(user.userId(), user); }
    public User getUserById(String id) { return users.get(id); }
    public Map<String, User> getUsers() { return users; }
}
