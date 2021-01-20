package concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/20 下午11:10
 */
public class AtomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        // 以原子方式将输入的数值与实例中的值相加，并返回旧值
        int getAndAdd = atomicInteger.getAndAdd(1);
        System.out.println("getAndAdd result is:" + getAndAdd);
        // 以原子方式将输入的数值与实例中的值相加，并返回新值
        int addAndGet = atomicInteger.addAndGet(2);
        System.out.println("addAndGet result is: " + addAndGet);
        // 如果输入的数值等于预期值，则以原子方式将该值设置为输入的值。
        boolean b = atomicInteger.compareAndSet(10, 13);
        System.out.println("compareAndSet result is: " + b);
        // 以原子方式自增 1，返回的是自增前的值
        int andIncrement = atomicInteger.getAndIncrement();
        System.out.println("getAndIncrement result is: " + andIncrement);
        // 以原子方式自增 1，返回自增后的值
        int incrementAndGet = atomicInteger.incrementAndGet();
        System.out.println("incrementAndGet result is: " + incrementAndGet);
        atomicInteger.lazySet(20);
        // 以原子方式设置为 newValue 的值，并返回旧值
        int andSet = atomicInteger.getAndSet(30);
        System.out.println("getAndSet result is:" + andSet);
    }
}
