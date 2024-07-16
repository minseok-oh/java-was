package codesquad.server.thread;

import codesquad.domain.entity.Session;
import codesquad.http.constant.HttpStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private final ExecutorService executor;

    public static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();
    public static final ThreadLocal<Boolean> sessionVerified = new ThreadLocal<>();

    public ThreadManager(final int thread) {
        executor = Executors.newFixedThreadPool(thread);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public void clear() {
        threadLocalSession.remove();
        sessionVerified.remove();
    }
}
