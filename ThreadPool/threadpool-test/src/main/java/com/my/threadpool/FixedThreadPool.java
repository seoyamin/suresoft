package com.my.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

    private static final int THREAD_WAITING = 3;
    private static final int MAX_THREAD = 5;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);

        for(int i=1 ; i<=THREAD_WAITING ; i++) {
            Runnable workerThread = new WorkerThread("cmd #" + i);
            executorService.execute(workerThread);
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {}
        System.out.println("Finished all Threads.");
    }
}
