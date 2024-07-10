package codesquad.domain.db;

import codesquad.domain.entity.Session;
import codesquad.domain.entity.User;
import codesquad.server.processor.session.SessionManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDatabase implements Database<Session, User> {
    private final Map<Session, User> sessionDB = new ConcurrentHashMap<>();

    @Override
    public Session append(User data) {
        Session session = SessionManager.createSession();
        sessionDB.put(session, data);
        return session;
    }

    @Override
    public User getById(Session id) { return sessionDB.get(id); }

    @Override
    public Map<Session, User> getAll() { return sessionDB; }

    public boolean isExist(Session id) { return sessionDB.containsKey(id); }

    public void deleteById(Session id) { sessionDB.remove(id); }
}
