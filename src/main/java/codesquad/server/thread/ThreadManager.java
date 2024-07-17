package codesquad.server.thread;

import codesquad.domain.entity.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private final ExecutorService executor;

    public static final ThreadLocal<Session> session = new ThreadLocal<>();

    public ThreadManager(final int thread) {
        executor = Executors.newFixedThreadPool(thread);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public void clear() {
        session.remove();
    }
}
