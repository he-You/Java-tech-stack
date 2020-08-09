### 线程阻塞工具类：LockSupport

特性：

- 可以在线程内任意位置阻塞线程
- 与 Thread.suspend()方法相比，弥补了 resume（）方法发生异常导致线程无法继续执行的情况
- 与 Object.wait()方法相比，它不需要先获得某个对象的锁，也不会抛出 InterruptedException 异常

```java
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
```

注：使用 park（）与 unPark()方法不管执行顺序如何，线程总能正常结束，而不会永久挂起。因为 LockSupport采用的是类似信号量的机制，为每个线程准备一个许可 permit，如果许可可用，测 park()方法会立即返回，并消费这个许可，如果许可不可用则线程阻塞。相反 unPark()则使得许可变得可用状态；另外：这个 permit 有且仅有一个。也正是这个原因即使 unPark()方法调用在 park()方法之前也不会发生线程挂起。



park()方法也不会使线程状态变为 Runnable，而是 Waiting 状态；



除了定时阻塞线程的作用，LockSupport.park()方法还支持中断影响，并且不会像其他的函数抛出 InterruptedException,但是我们仍然可以从 Thread.interrupted()等方法中获得中断标记



