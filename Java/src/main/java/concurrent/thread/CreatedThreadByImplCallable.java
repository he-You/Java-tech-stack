package concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过 callable和 futureTask 创建线程
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午3:05
 */
public class CreatedThreadByImplCallable implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getId() + " is running");
        return Thread.currentThread().getId();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Long> task = new FutureTask<>(new CreatedThreadByImplCallable());
        new Thread(task).start();
        System.out.println("等待完成任务");
        Long result = task.get();
        System.out.println("任务结果：" + result);
    }
}
