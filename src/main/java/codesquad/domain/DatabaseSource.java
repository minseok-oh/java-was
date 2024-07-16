package codesquad.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSource {
    private static final String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
    private static final String username = "sa";
    private static final String password = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public static void init() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        createTable();
    }

    public static void createTable() {
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "userid VARCHAR(255) UNIQUE NOT NULL, " +
                    "nickname VARCHAR(255) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL)";

            String createPostTableSQL = "CREATE TABLE IF NOT EXISTS posts (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "userid VARCHAR(255), " +
                    "title VARCHAR(255)," +
                    "contents TEXT, " +
                    "FOREIGN KEY (userid) REFERENCES users(userid))";

            String createCommetTableSQL = "CREATE TABLE IF NOT EXISTS comments (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "postid INT, " +
                    "userid VARCHAR(255), " +
                    "contents TEXT, " +
                    "FOREIGN KEY (postid) REFERENCES posts(id), " +
                    "FOREIGN KEY (userid) REFERENCES users(userid))";

            statement.execute(createUserTableSQL);
            statement.execute(createPostTableSQL);
            statement.execute(createCommetTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
