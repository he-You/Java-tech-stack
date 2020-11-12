package designPattern.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 调度函数
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/12 23:37
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Executor executors = Executors.newCachedThreadPool();
        DataFactory factory = new DataFactory();

        int bufferSize = 1024;
        Disruptor<PCData> dataDisruptor = new Disruptor<PCData>(factory,
                bufferSize, executors, ProducerType.MULTI, new BlockingWaitStrategy());
        dataDisruptor.handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer());
        dataDisruptor.start();

        RingBuffer<PCData> ringBuffer = dataDisruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            byteBuffer.putLong(0,1);
            producer.pushData(byteBuffer);
            Thread.sleep(100);
            System.out.println("add data "+l);
        }
    }
}
