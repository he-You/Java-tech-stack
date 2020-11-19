package concurrent.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 返回固定线程数的线程池
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/15 14:33
 */
public class NewFixedThreadPoolDemo {
    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "Thread ID:"+Thread.currentThread().getId());
            Thread.currentThread().getId();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask task = new MyTask();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }
    }
}
