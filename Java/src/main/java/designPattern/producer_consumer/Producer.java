package designPattern.producer_consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/10 23:52
 */
public class Producer implements Runnable {
    private volatile  boolean isRunning = true;
    // 内存缓冲区
    private BlockingQueue<PCData> queue;
    // 总数，原子操作
    private static AtomicInteger count = new AtomicInteger();

    private static final int SLEEPTIME = 1000;

    public Producer(BlockingQueue<PCData> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        PCData data = null;
        Random random = new Random();
        System.out.println("start producer id="+ Thread.currentThread().getId());
        try{
            while (isRunning){
                Thread.sleep(random.nextInt(SLEEPTIME));
            }
        } catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop(){
        isRunning = false;
    }
}
