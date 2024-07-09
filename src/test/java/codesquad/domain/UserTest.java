package codesquad.domain;

import codesquad.domain.db.UserDatabase;
import codesquad.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private final UserDatabase userDatabase = new UserDatabase();

    @Test
    @DisplayName("user 정보를 입력하면 이에 해당하는 user 객체가 생성됩니다")
    void appendUser() {
        User user = new User("userId", "nickName", "password");
        userDatabase.appendUser(user);
        assertEquals(user, userDatabase.getUserById("userId"));
    }

    @Test
    @DisplayName("여러 user를 한번에 불러 올 수 있습니다")
    void getUsers() {
        User user1 = new User("userId1", "nickName1", "password1");
        User user2 = new User("userId2", "nickName2", "password2");
        User user3 = new User("userId3", "nickName3", "password3");

        userDatabase.appendUser(user1);
        userDatabase.appendUser(user2);
        userDatabase.appendUser(user3);

        assertEquals(3, userDatabase.getUsers().size());
    }
}
