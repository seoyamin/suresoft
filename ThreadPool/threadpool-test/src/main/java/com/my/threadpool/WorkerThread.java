package com.my.threadpool;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class WorkerThread implements Runnable {

    private final String command;
    private static final Logger LOGGER = Logger.getLogger(WorkerThread.class.getName());

    public WorkerThread(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
            readFile();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " End.");
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFile() throws IOException {
        File file = new File("C:\\Users\\user\\Desktop\\Task\\threadpool-test\\src\\main\\resources\\readme.txt");
        FileReader filereader = new FileReader(file);

        int c = 0;
        String result = "";

        while((c = filereader.read()) != -1){
            result += (char) c;
        }
        System.out.println(result);
        filereader.close();
    }
}
