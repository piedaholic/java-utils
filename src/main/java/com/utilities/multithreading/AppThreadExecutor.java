package com.utilities.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppThreadExecutor {
    private static int poolsize = 0;
    private static ExecutorService service = null;
    private static final int THREAD_SLEEP_TIME = 10000;

    public static void execute(Runnable command) throws Exception {
	if (poolsize <= 0) {
	    try {
		poolsize = 2;
	    } catch (Exception e) {
		poolsize = 25;
	    }
	}

	if (service == null || service.isShutdown()) {
	    service = Executors.newFixedThreadPool(poolsize);
	}

	try {

	    Future<?> future = service.submit(command);
	    while (!future.isDone()) {
		Thread.sleep(THREAD_SLEEP_TIME);
	    }
	    System.out.println(future.get().toString());
	} catch (Exception e) {
	    System.out.println(e);
	}

    }

    public static void shutdownPool() {
	if (service != null && !service.isShutdown()) {
	    service.shutdown();
	}

    }
}
