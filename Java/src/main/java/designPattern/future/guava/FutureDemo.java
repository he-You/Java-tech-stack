package designPattern.future.guava;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import designPattern.future.jdk.RealData;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Guava 对 Future 模式的支持
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/18 22:35
 */
public class FutureDemo {
    public static void main(String[] args) throws InterruptedException {
        // MoreExecutors.listeningDecorator()将普通的线程池包装成一个含通知功能的 Future线程池
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        // 将 Callable任务提交到线程池
        ListenableFuture<String> task = service.submit(new RealData("x"));
        // 添加回调函数
        task.addListener(()->{
            System.out.println("异步处理成功");
            try {
                System.out.println(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        },MoreExecutors.directExecutor());
        System.out.println("main task done....");
        Thread.sleep(3000);
    }
}
