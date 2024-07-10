package codesquad.domain.db;

import codesquad.domain.entity.Session;
import codesquad.domain.entity.User;
import codesquad.server.processor.session.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionDatabaseTest {
    private final SessionDatabase sessionDatabase = new SessionDatabase();

    @Test
    @DisplayName("session 정보를 입력하면 이에 해당하는 session 객체가 생성됩니다")
    void appendUser() {
        User user = new User("user1", "nickname1", "password1");
        Session session = sessionDatabase.append(user);
        assertEquals(user, sessionDatabase.getById(session));
    }

    @Test
    @DisplayName("여러 session을 한번에 불러 올 수 있습니다")
    void getUsers() {
        User user1 = new User("userId1", "nickName1", "password1");
        User user2 = new User("userId2", "nickName2", "password2");
        User user3 = new User("userId3", "nickName3", "password3");

        sessionDatabase.append(user1);
        sessionDatabase.append(user2);
        sessionDatabase.append(user3);

        assertEquals(3, sessionDatabase.getAll().size());
    }

    @Test
    @DisplayName("해당 session이 존재하는지 확인할 수 있습니다")
    void existSession() {
        User user = new User("userId1", "nickName1", "password1");

        Session session1 = sessionDatabase.append(user);
        Session session2 = SessionManager.createSession();

        assertTrue(sessionDatabase.isExist(session1));
        assertFalse(sessionDatabase.isExist(session2));
    }
}
