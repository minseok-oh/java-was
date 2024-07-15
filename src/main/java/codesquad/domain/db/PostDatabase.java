package codesquad.domain.db;

import codesquad.domain.connect.DatabaseConnector;
import codesquad.domain.entity.Post;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class PostDatabase implements Database<Integer, Post> {

    @Override
    public Integer append(Post data) {
        Integer id = null;
        try (Connection connection = DatabaseConnector.connect()){
            String createPostSQL = "INSERT INTO Post (%s, %s) VALUES (?, ?)".formatted(data.user().userId(), data.content());
            id = connection.prepareStatement(createPostSQL).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Post getById(Integer id) {
        Post post = null;
        try (Connection connection = DatabaseConnector.connect()) {
            String getPostSQL = "SELECT * FROM Post WHERE id = %s".formatted(id);
            var resultSet = connection.prepareStatement(getPostSQL).executeQuery();
            if (resultSet.next()) return null;
            post = new Post(null, resultSet.getString("contents"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Map<Integer, Post> getAll() {
        Map<Integer, Post> result = new HashMap<>();
        try (Connection connection = DatabaseConnector.connect()){
            String getAllPostsSQL = "SELECT * FROM Post";
            var resultSet = connection.prepareStatement(getAllPostsSQL).executeQuery();
            while (resultSet.next()) {
                result.put(resultSet.getInt("id"), new Post(null, resultSet.getString("contents")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = DatabaseConnector.connect()) {
            String deletePostSQL = "DELETE FROM Post WHERE id = %s".formatted(id);
            connection.prepareStatement(deletePostSQL).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
