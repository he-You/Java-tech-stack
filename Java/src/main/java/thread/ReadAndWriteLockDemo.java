package thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 demo
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/9 12:16
 */
public class ReadAndWriteLockDemo {
    private static Lock lock = new ReentrantLock();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = readWriteLock.readLock();

    private static Lock writeLock = readWriteLock.writeLock();

    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(2000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int index) throws InterruptedException {
        lock.lock();
        try {
            Thread.sleep(1000);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadAndWriteLockDemo demo = new ReadAndWriteLockDemo();
        Runnable readRunnale = () -> {
            try {
                demo.handleRead(readLock);
                // demo.handleRead(lock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable writeRunnale = () -> {
            try {
                demo.handleWrite(writeLock,new Random().nextInt());
                // demo.handleWrite(lock,new Random().nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnale).start();
        }

        for (int i = 18; i < 20; i++) {
            new Thread(writeRunnale).start();
        }
    }
}
