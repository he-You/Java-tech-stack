package concurrent.thread;

/**
 * 通过实现 Runnable 创建线程
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午2:48
 */
public class CreatedThreadByImplRunnable {
    public static void main(String[] args) {
        RunnableThreadTest runnableThreadTest = new RunnableThreadTest();
        new Thread(runnableThreadTest, "线程1").start();
        new Thread(runnableThreadTest, "线程2").start();
    }

    /**
     * 实现Runnable接口的方式
     */
    static class RunnableThreadTest implements Runnable{
        @Override
        public void run() {
            for (int i=0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName()  + " is running: " + i);
            }
        }
    }

    /**
     * 实现 Runnable 的匿名内部类
     */
    public void createdThreadByImplRunnableInnerClass() {
        // 初始化线程实例
        Thread thread = new Thread(() -> System.out.println("匿名内部类..."));
        thread.start();
    }
}
