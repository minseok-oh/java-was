package codesquad.domain.db;

import codesquad.domain.DatabaseSource;
import codesquad.domain.entity.Comment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CommentDatabase implements Database<Integer, Comment> {

    @Override
    public Integer append(Comment data) {
        Integer id = null;
        try (Connection connection = DatabaseSource.connect()){
            String createCommentSQL = "INSERT INTO comments (userid, postid, contents) VALUES (?, ?, ?)";
            var pstmt = connection.prepareStatement(createCommentSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, data.userid());
            pstmt.setString(2, data.postid());
            pstmt.setString(3, data.contents());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) id = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Comment getById(Integer id) {
        return null;
    }

    @Override
    public Map<Integer, Comment> getAll() {
        Map<Integer, Comment> result = new HashMap<>();
        try (Connection connection = DatabaseSource.connect()) {
            String getAllCommentsSQL = "SELECT * FROM comments";
            var resultSet = connection.prepareStatement(getAllCommentsSQL).executeQuery();
            while (resultSet.next()) {
                result.put(resultSet.getInt("id"), new Comment(resultSet.getString("userid"), resultSet.getString("postid"), resultSet.getString("contents")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
