package concurrent.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类：LockSupport
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/9 17:54
 */
public class LockSupportDemo {
    public static Object u = new Object();
    private static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    private static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread (String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in:"+getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
