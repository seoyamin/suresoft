package com.my.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TuningThreadPool {

    private static final int THREAD_WAITING = 12;

    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("# availableProcessors = " + availableProcessors);
        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);

        for(int i=1 ; i<=THREAD_WAITING ; i++) {
            Runnable workerThread = new WorkerThread("cmd #" + i);
            executorService.execute(workerThread);
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {}
        System.out.println("Finished all Threads.");
    }
}
