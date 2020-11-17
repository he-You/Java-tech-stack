package designPattern.future.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/17 23:02
 */
public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 构造 FutureTask
        FutureTask<String> futureTask = new FutureTask<String>(new RealData("a"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 执行 FutureTask
        executorService.submit(futureTask);
        System.out.println("请求完毕");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据="+futureTask.get());
    }
}
