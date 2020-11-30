package io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO 实现的 Echo 服务端
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/30 22:29
 */
public class EchoServerByNio {
    private Selector selector;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    public static Map<Socket,Long> time_stat = new HashMap<>(10240);

    private void startServer() throws IOException {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(),8000);
        //InetSocketAddress inetSocketAddress =
        serverSocketChannel.socket().bind(inetSocketAddress);
        // 将ServerSocketChannel绑定到 Selector上，并注册它感兴趣的时间为 Accept
        SelectionKey acceptKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        for(;;){
            selector.select();
            Set readyKeys = Collections.singleton(selector.select());
            Iterator i = readyKeys.iterator();
            long e = 0l;
            while(i.hasNext()){
                SelectionKey selectionKey = (SelectionKey) i.next();
                i.remove();;

                if(selectionKey.isAcceptable()){
                    doAccept(selectionKey);
                } else if(selectionKey.isValid() && selectionKey.isReadable()){
                    if (!time_stat.containsKey(((SocketChannel)selectionKey.channel()).socket())){
                        time_stat.put(((SocketChannel)selectionKey.channel()).socket(),System.currentTimeMillis());
                    }
                    doRead(selectionKey);
                } else if(selectionKey.isValid() && selectionKey.isWritable()){
                    doWrite(selectionKey);
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel)selectionKey.channel()).socket());
                    System.out.println("spend:"+(e-b)+"ms");
                }
            }
        }
    }

    private void doAccept(SelectionKey selectionKey){
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientChannel;

        try{
            clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);

            SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accept connection from "+clientAddress.getHostAddress()+".");
        } catch (IOException e) {
            System.out.println("Failed to accept new Client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey selectionKey){

    }

    private void doWrite(SelectionKey selectionKey){

    }
}
