package io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程 Echo 服务器
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/26 22:32
 */
public class MultiThreadEchoServer {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable {
        Socket socketClient;

        public HandleMsg(Socket socketClient) {
            this.socketClient = socketClient;
        }

        @Override
        public void run() {
            try (BufferedReader is = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                 PrintWriter os = new PrintWriter(socketClient.getOutputStream(), true)) {
                // 从 InputStream当中读取客户端所发送的数据;
                String inputLine = null;
                long b = System.currentTimeMillis();
                while ((inputLine = is.readLine()) != null) {
                    os.println(inputLine);
                }
                long e = System.currentTimeMillis();
                System.out.println("spend:" + (e - b) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket echoServer = null;
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + "connect!");
                executorService.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
