package codesquad.domain;

import codesquad.server.processor.message.HttpGenerator;
import codesquad.server.processor.message.HttpParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public class DatabaseSource {
    private static final String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
    private static final String username = "sa";
    private static final String password = "";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSource.class);

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public static void init() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        createTable();
    }

    public static void createTable() {
        try (Connection connection = connect(); Statement statement = connection.createStatement();
             InputStream dropSQL = DatabaseSource.class.getResourceAsStream("/sql/drop.sql");
             InputStream createSQL = DatabaseSource.class.getResourceAsStream("/sql/create.sql")) {

            String dropTableSQL = new String(Objects.requireNonNull(dropSQL).readAllBytes());
            logger.debug(dropTableSQL);
            statement.execute(dropTableSQL);

            String[] createTableSQL = new String(Objects.requireNonNull(createSQL).readAllBytes()).split("\n");
            for (String sql: createTableSQL) {
                logger.debug(sql);
                statement.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
