package concurrent.thread;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/27 下午11:18
 */
public class TestCaughtExceptionDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            int i = 1 / 0;//发生异常
        });
        //自定义未捕获异常处理器
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("已捕获的异常为："+e.getMessage());
            }
        });
        thread.start();
    }
}
