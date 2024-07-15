package codesquad.domain.db;

import codesquad.domain.connect.DatabaseConnector;
import codesquad.domain.entity.User;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase implements Database<String, User> {

    @Override
    public String append(User user) {
        try (Connection connection = DatabaseConnector.connect()) {
            String createUserSQL = "INSERT INTO User (%s, %s, %s) VALUES (?, ?)".formatted(user.userId(), user.nickName(), user.password());
            connection.prepareStatement(createUserSQL).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user.userId();
    }

    @Override
    public User getById(String id) {
        User user = null;
        try (Connection connection = DatabaseConnector.connect()) {
            String getUserSQL = "SELECT * FROM User WHERE userid = %s".formatted(id);
            var resultSet = connection.prepareStatement(getUserSQL).executeQuery();
            if (!resultSet.next()) return null;
            user = new User(resultSet.getString("userid"), resultSet.getString("nickname"), resultSet.getString("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Map<String, User> getAll() {
        Map<String, User> result = new HashMap<>();
        try (Connection connection = DatabaseConnector.connect()){
            String getAllUsersSQL = "SELECT * FROM User";
            var resultSet = connection.prepareStatement(getAllUsersSQL).executeQuery();
            while (resultSet.next()) {
                result.put(resultSet.getString("userid"), new User(resultSet.getString("userid"), resultSet.getString("nickname"), resultSet.getString("password")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteById(String id) {
        try (Connection connection = DatabaseConnector.connect()) {
            String deleteUserSQL = "DELETE FROM User WHERE userid = %s".formatted(id);
            connection.prepareStatement(deleteUserSQL).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
