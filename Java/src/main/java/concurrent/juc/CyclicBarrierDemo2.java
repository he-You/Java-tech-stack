package concurrent.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/2/1 下午11:39
 */
public class CyclicBarrierDemo2 {
    // 创建一个 CyclicBarrier 实例
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(()->{
            try {
                System.out.println(Thread.currentThread()+"step1");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+"step2");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+"step3");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(()->{
            try {
                System.out.println(Thread.currentThread()+"step1");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+"step2");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread()+"step3");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }
}
