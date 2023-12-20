package com.quiz.disk;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class WorkerThread implements Runnable {
    private static final String MERGE_DIR = "C:\\Users\\user\\Desktop\\Task\\[2023.11] #10 대용량 정렬\\QuizDisk\\merged";

    private final int roundId;
    private final int threadId;

    private File file1, file2;

    public WorkerThread(int roundId, int threadId, File file1, File file2) {
        this.roundId = roundId;
        this.threadId = threadId;
        this.file1 = file1;
        this.file2 = file2;
        System.out.println("Thread #" + threadId + " Created");
    }

    @Override
    public void run() {
        try {
            System.out.println("[Round " + roundId + "] Thread #" + threadId + " Merging " + file1.getName() + " & " + file2.getName());
            externalSorting();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void externalSorting() throws IOException {
        String newFilePath;
        newFilePath = MERGE_DIR + "\\merged" + (Main.mId++) + ".txt";
        FileOutputStream outputStream = new FileOutputStream(newFilePath);

        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file1));
        PriorityQueue<Integer> priorityQueue1 = readAllLines(bufferedReader1);

        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
        PriorityQueue<Integer> priorityQueue2 = readAllLines(bufferedReader2);

        int numToWrite;
        while(!priorityQueue1.isEmpty() && !priorityQueue2.isEmpty()) {
            numToWrite = (priorityQueue1.peek() > priorityQueue2.peek()) ? priorityQueue1.poll() : priorityQueue2.poll();
            writeNum(numToWrite, outputStream);
        }

        while(!priorityQueue1.isEmpty()) {
            numToWrite = priorityQueue1.poll();
            writeNum(numToWrite, outputStream);
        }

        while(!priorityQueue2.isEmpty()) {
            numToWrite = priorityQueue2.poll();
            writeNum(numToWrite, outputStream);
        }

        Main.fileTaskQueue.add(new File(newFilePath));
        System.out.println("----------");
    }

    /* BufferReader로 파일 내 모든 숫자를 읽어서 PriorityQueue로 반환 */
    private static PriorityQueue<Integer> readAllLines(BufferedReader bufferedReader) throws IOException {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        String str;

        while((str = bufferedReader.readLine()) != null) {
            int num = Integer.parseInt(str);
            priorityQueue.add(num);
        }

        return priorityQueue;
    }

    /* OutputStream으로 숫자를 파일에 쓰기 */
    private static void writeNum(int num, OutputStream outputStream) throws IOException {
        outputStream.write(String.valueOf(num).getBytes());
        outputStream.write(10);
    }
}
