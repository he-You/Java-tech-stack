package concurrent.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在线程池中使用 CountDownLatch
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/28 下午10:22
 */
public class CountDownLatchDemo2 {
    /**
     * 创建实例
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 将线程 A添加到线程池中
        executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("child threadOne over!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        // 将线程 B 添加到线程池中
        executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("child threadTwo over!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        });
        System.out.println("wait all child thread over!");
        // 等待子线程执行完毕，返回
        countDownLatch.await();
        System.out.println("all child thread over");
        executorService.shutdown();
    }
}
