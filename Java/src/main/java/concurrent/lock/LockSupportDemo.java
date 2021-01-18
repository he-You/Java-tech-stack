package concurrent.lock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/18 下午10:23
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        FIFOMutex mutex = new FIFOMutex();
        MyThread a1 = new MyThread("a1", mutex);
        MyThread a2 = new MyThread("a2", mutex);
        MyThread a3 = new MyThread("a3", mutex);

        a1.start();
        a2.start();
        a3.start();

        a1.join();
        a2.join();
        a3.join();

        assert MyThread.count == 300;
        System.out.print("Finished");
    }

    static class MyThread extends Thread {
        private String name;
        private FIFOMutex mutex;
        public static int count;

        public MyThread(String name, FIFOMutex mutex) {
            this.name = name;
            this.mutex = mutex;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                mutex.lock();
                count++;
                System.out.println("name:" + name + "  count:" + count);
                mutex.unlock();
            }
        }
    }

    /**
     * 假设现在需要实现一种FIFO类型的独占锁，可以把这种锁看成是ReentrantLock的公平锁简单版本，且是不可重入的，
     * 就是说当一个线程获得锁后，其它等待线程以FIFO的调度方式等待获取锁。
     */
    static class FIFOMutex {
        // 是否上锁的标志
        private final AtomicBoolean locked = new AtomicBoolean(false);
        // 阻塞的线程队列
        private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

        public void lock() {
            boolean wasInterrupted = false;
            Thread current = Thread.currentThread();
            waiters.add(current);

            // Block while not first in queue or cannot acquire lock
            // 如果当前线程不在队首，或锁已被占用，则当前线程阻塞。这个判断的意图其实就是：锁必须由队首元素拿到
            while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
                LockSupport.park(this);
                // ignore interrupts while waiting
                if (Thread.interrupted()) {
                    wasInterrupted = true;
                }
            }
            // 删除队首元素
            waiters.remove();
            // reassert interrupt status on exit
            if (wasInterrupted) {
                current.interrupt();
            }
        }

        public void unlock() {
            locked.set(false);
            // 将等待队列中的队首线程唤醒
            LockSupport.unpark(waiters.peek());
        }
    }
}
