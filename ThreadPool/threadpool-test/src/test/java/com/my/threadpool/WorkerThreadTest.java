package com.my.threadpool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.*;

class WorkerThreadTest {

    @Test
    @DisplayName("CachedThreadPool 테스트")
    void testCachedThreadPool() {
        int waitingThreads = 3;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        for(int i=1 ; i<waitingThreads ; i++) {
            WorkerThread workerThread = new WorkerThread("cmd #" + i);
            executor.execute(workerThread);
        }

        assertEquals(waitingThreads, executor.getPoolSize());
        assertEquals(0, executor.getQueue().size());
    }
}