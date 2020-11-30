package io.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * EchoClient 定义
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/30 23:04
 */
public class EchoClient {
    private LinkedList<ByteBuffer> outq;
    EchoClient(){
        outq = new LinkedList<>();
    }

    public LinkedList<ByteBuffer> getOutputQueue(){
        return outq;
    }

    public void enqueue(ByteBuffer byteBuffer){
        outq.addFirst(byteBuffer);
    }
}
