package concurrent.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/31 下午5:33
 */
public class CyclicBarrierDemo {
    /**
     * 创建一个 CyclicBarrier实例，添加一个所有子线程全部到达屏障后执行的任务
     *
     * @param parties:计数器的初始值
     * @param Runnable:是当计数器为 0 时需要执行的任务
     */
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
        System.out.println(Thread.currentThread() + "task1 merge result");
    });

    public static void main(String[] args) {
        // 创建一个线程个数固定为 2 的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 将线程 A添加到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + "task1-1");
                System.out.println(Thread.currentThread() + "enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + "enter out barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        // 将线程 B添加到线程池
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread() + "task1-2");
                System.out.println(Thread.currentThread() + "enter in barrier");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread() + "enter out barrier");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }
}
