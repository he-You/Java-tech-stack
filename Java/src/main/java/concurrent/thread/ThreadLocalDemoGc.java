package concurrent.thread;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 回收局部变量
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/9/2 21:35
 */
public class ThreadLocalDemoGc {
    private static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + "is gc");
        }
    };

    static volatile CountDownLatch countDownLatch = new CountDownLatch(10000);

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (t1.get() == null) {
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString()+"is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId()+"：create SimpleDateFormat");
                }
                Date date = t1.get().parse("2020-09-02 20:20:"+i % 60);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new ParseDate(i));
        }
        countDownLatch.wait();
        System.out.println("mission complete!!");
        t1 = null;
        System.gc();
        System.out.println("first GC complete");
        // 在设置 ThreadLocal 的时候，会清除 ThreadLocalMap 中的无效对象
        t1 = new ThreadLocal<>();
        countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new ParseDate(i));
        }
        countDownLatch.await();
        Thread.sleep(10000);
        System.gc();
        System.out.println("second GC complete!!");
    }
}
