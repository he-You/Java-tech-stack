package concurrent.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟并发
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/2/25 下午10:14
 */
public class CountDownLatchDemo3 {
    private static volatile int count = 0;
    private static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            // 等待所有线程就绪
            countDownLatch.await();
            new Thread(() -> {
                synchronized (object){
                    for (int j = 0; j < 10; j++) {
                        count++;
                    }
                    System.out.println(count);
                }
            });
        }
        Thread.sleep(3000);
        countDownLatch.countDown();
    }
}
