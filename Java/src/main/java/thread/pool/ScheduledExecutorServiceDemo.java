package thread.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * scheduleAtFixedRate:initialDelay+period,即初始延时+周期时间
 * scheduleWithFixedDelay:只有周期延时
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/15 15:27
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        //
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis()/1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },1,2, TimeUnit.SECONDS);
    }
}
