package thread.pool;

import java.util.concurrent.*;

/**
 * 自定义线程池与拒绝策略的使用
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/20 22:08
 */
public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        // 自定义线程池
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10),
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString()+" is discard");
                    }
                });
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.submit(task);
            Thread.sleep(10);
        }
    }
}
