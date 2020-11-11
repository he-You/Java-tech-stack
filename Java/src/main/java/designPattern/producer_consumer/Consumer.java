package designPattern.producer_consumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * 消费者实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/11 23:10
 */
public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue;

    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Cosumer id=" + Thread.currentThread().getId());
        Random random = new Random();
        try {
            while (true) {
                // 提取任务
                PCData data = queue.take();
                if (data != null){
                    int re = data.getData()*data.getData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",data.getData(),data.getData(),re));
                    Thread.sleep(random.nextInt(SLEEPTIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
