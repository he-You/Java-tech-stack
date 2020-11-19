package concurrent.thread;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/10 20:42
 */
public class RateLimiterDemo {
    private static RateLimiter limiter = RateLimiter.create(2);

    public static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
        }
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 50; i++) {
//            limiter.acquire();
//            new Thread(new Task()).start();
//        }

        for (int i = 0; i < 50; i++) {
            if (!limiter.tryAcquire()) {
                continue;
            }
            new Thread(new Task()).start();
        }
    }
}
