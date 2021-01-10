package concurrent.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午3:12
 */
public class CreatedThreadByThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            threadPool.execute(()-> System.out.println(Thread.currentThread().getName() + " is running"));
        }
    }
}
