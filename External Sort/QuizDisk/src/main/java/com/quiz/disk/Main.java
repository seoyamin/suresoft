package com.quiz.disk;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {

//    private static final long SIZE = 1024L * 1024 * 1024 * 4;
//    private static final int NUM_FILES = 1024;

    private static final long SIZE = 1024L * 4;
    private static final int NUM_FILES = 256;

    private static final int NUM_THREADS = 256;
    private static final int SIZE_PER_FILES = (int) (SIZE / NUM_FILES);
    private static final int MIN = 1001;
    private static final int MAX = 9999;
    private static final String INPUT_DIR = "C:\\Users\\user\\Desktop\\Task\\[2023.11] #10 대용량 정렬\\QuizDisk\\input";
    private static final String OUTPUT_DIR = "C:\\Users\\user\\Desktop\\Task\\[2023.11] #10 대용량 정렬\\QuizDisk\\output";

    public static ConcurrentLinkedQueue<File> fileTaskQueue = new ConcurrentLinkedQueue<>();
    public static int mId = 1;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //init();
        externalSorting();
        //sortWithMultiThreads();
    }

    /* 사전 작업 - [SIZE_PER_FILES]개의 자연수를 갖는 Input File을 [NUM_FILES]개 생성 */
    private static void init() throws IOException {
        for(int fileId=1 ; fileId<=NUM_FILES ; fileId++) {
            String filePath = INPUT_DIR + "\\input" + fileId + ".txt";
            FileOutputStream outputStream = new FileOutputStream(filePath);

            for(int numId=0 ; numId<SIZE_PER_FILES ; numId++) {
                int num = (int) (Math.random() * (MAX - MIN + 1) + MIN);
                outputStream.write(String.valueOf(num).getBytes());
                outputStream.write(10);
            }
            outputStream.close();
        }
    }

    /* 동일한 라운드의 파일 병합은 여러 개의 스레드에서 동시에 수행 */
    private static void sortWithMultiThreads() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        int roundNum = calculateRoundNum();
        int roundId = 1, threadId = 1;
        fileTaskQueue.addAll(Arrays.asList(Objects.requireNonNull(new File(INPUT_DIR).listFiles())));

        while (roundId <= roundNum && fileTaskQueue.size() > 1) {
            System.out.println("[" + roundId + "/" + roundNum + "] queue size = " + fileTaskQueue.size());
            while(fileTaskQueue.size() > 1) {
                Future<?> future = executorService.submit(new WorkerThread(roundId, threadId, fileTaskQueue.poll(), fileTaskQueue.poll()));
                threadId++;

                if(fileTaskQueue.size() <= 1) future.get();  // 현재 라운드의 마지막 스레드이면 작업 완료까지 wait
            }
            roundId++;
        }


    }

    /* 전체 라운드 수 구하기 */
    private static int calculateRoundNum() {
        int roundNum = 0;
        while(Math.pow(2, roundNum) <= NUM_FILES) {
            roundNum++;
        }

        return roundNum;
    }



    /* 두 파일씩 읽어서 하나의 정렬된 파일로 합치기 */
    private static void externalSorting() throws IOException {
        File[] files = new File(INPUT_DIR).listFiles();
        Queue<File> fileTasks = new LinkedList<>(Arrays.asList(files));

        int mergedId = 1;
        FileOutputStream outputStream;

        while(fileTasks.size() > 1) {
            String newFilePath;    // 새로 생성되는 병합 파일
            if(fileTasks.size() == 2) newFilePath = OUTPUT_DIR + "\\result.txt";
            else newFilePath = INPUT_DIR + "\\merged" + (mergedId++) + ".txt";
            outputStream = new FileOutputStream(newFilePath);

            BufferedReader bufferedReader1 = new BufferedReader(new FileReader(Objects.requireNonNull(fileTasks.poll())));
            PriorityQueue<Integer> priorityQueue1 = readAllLines(bufferedReader1);

            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(Objects.requireNonNull(fileTasks.poll())));
            PriorityQueue<Integer> priorityQueue2 = readAllLines(bufferedReader2);


            int numToWrite;     // 새로운 병합 파일에 작성할 숫자 (내림차순)
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

            fileTasks.add(new File(newFilePath));
            System.out.println("----------");
        }
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
