package codesquad;

import codesquad.server.Router;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static codesquad.server.Router.userData;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RouterTest {
    private final Router router = new Router();

    @Test
    void createUser() throws UnsupportedEncodingException {
        String param = "userId=userId&nickName=nickName&password=password";
        router.createUser(param);
        assertNotNull(userData.getUserById("userId"));
    }
}
