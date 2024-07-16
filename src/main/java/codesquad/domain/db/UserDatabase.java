package codesquad.domain.db;

import codesquad.domain.DatabaseSource;
import codesquad.domain.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase implements Database<String, User> {

    @Override
    public String append(User user) {
        try (Connection connection = DatabaseSource.connect()) {
            String createUserSQL = "INSERT INTO users (userid, nickname, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(createUserSQL);
            pstmt.setString(1, user.userid());
            pstmt.setString(2, user.nickname());
            pstmt.setString(3, user.password());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user.userid();
    }

    @Override
    public User getById(String id) {
        User user = null;
        try (Connection connection = DatabaseSource.connect()) {
            String getUserSQL = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUserSQL);
            pstmt.setString(1, id);
            var resultSet = pstmt.executeQuery();
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
        try (Connection connection = DatabaseSource.connect()){
            String getAllUsersSQL = "SELECT * FROM users";
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
        try (Connection connection = DatabaseSource.connect()) {
            String deleteUserSQL = "DELETE FROM users WHERE userid = %s".formatted(id);
            connection.prepareStatement(deleteUserSQL).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
