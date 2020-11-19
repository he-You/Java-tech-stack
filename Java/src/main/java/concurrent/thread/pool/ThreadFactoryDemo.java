package concurrent.thread.pool;

import java.util.concurrent.*;

/**
 * 自定义线程工厂
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/20 22:23
 */
public class ThreadFactoryDemo {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10),
                // 自定义线程创建工厂
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        System.out.println("create "+ thread);
                        return thread;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is discard");
                    }
                });
    }
}
