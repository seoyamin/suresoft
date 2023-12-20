package com.my.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {

    private static final int THREAD_WAITING = 12;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i=1 ; i<=THREAD_WAITING ; i++) {
            WorkerThread workerThread = new WorkerThread("cmd #" + i);
            executorService.execute(workerThread);
        }

        executorService.shutdown();
        while(!executorService.isTerminated()) {}
        System.out.println("Finished all Threads");
    }
}
