package codesquad.domain.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private static final String jdbcURL = "jdbc:h2:tcp://localhost/~/test"; // 메모리 내 데이터베이스: jdbc:h2:mem:test
    private static final String username = "sa";
    private static final String password = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public static void init() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        //createTable();
    }

    public static void createTable() {
        try (Connection connection = connect(); Statement statement = connection.createStatement()) {
            String createUserTableSQL = "CREATE TABLE IF NOT EXISTS User (" +
                    "userid VARCHAR(255) PRIMARY KEY, " +
                    "nickname VARCHAR(255) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL)";

            String createPostTableSQL = "CREATE TABLE IF NOT EXISTS Post (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT, " +
                    "contents TEXT, " +
                    "FOREIGN KEY (user_id) REFERENCES User(id))";

            statement.execute(createUserTableSQL);
            statement.execute(createPostTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
