package concurrent.thread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午3:08
 */
public class CreatedThreadByTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        // 每隔1秒执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }, 0 , 1000);
    }
}
