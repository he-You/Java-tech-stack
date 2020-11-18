package designPattern.future.guava;

import com.google.common.util.concurrent.*;
import designPattern.future.jdk.RealData;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Executors;

/**
 * 使用Futures 工具类将 FutureCallTask 接口注册到给定的 Future 中，从而实现对 Future 的异常处理
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/18 23:04
 */
public class FutureDemoWithException {
    public static void main(String[] args) throws InterruptedException {
        // MoreExecutors.listeningDecorator()将普通的线程池包装成一个含通知功能的 Future线程池
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        // 将 Callable任务提交到线程池
        ListenableFuture<String> task = service.submit(new RealData("x"));

        Futures.addCallback(task, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String s) {
                System.out.println("异步处理成功，result="+s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("异步处理失败，e="+throwable);
            }
        },MoreExecutors.newDirectExecutorService());
        System.out.println("main task done.....");
        Thread.sleep(3000);
    }
}
