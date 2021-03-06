package io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * 基于 Socket客户端实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/28 21:48
 */
public class MultiThreadSocketClient {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static final int SLEEP_TIME = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable {
        @Override
        public void run() {
            try (Socket client = new Socket();
                 PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
                client.connect(new InetSocketAddress("localhost", 8000));
                writer.println("H");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println("e");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println("l");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println("l");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println("o");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println("!");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println();
                writer.flush();
                System.out.println("from server: " + reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        for (int i = 0; i < 10; i++) {
            executorService.execute(echoClient);
        }
    }
}
