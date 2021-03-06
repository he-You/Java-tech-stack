package concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/6 下午3:54
 */
public class AtomicIntegerArrayDemo {
    private static int[] array = new int[]{1, 2, 3, 4, 5};
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array);
        int index0 = 0;
        int delta = 2;
        // 返回更新后（拷贝）数组下标为 index0 并且加上 delta 之后的值
        int addAndGet = atomicIntegerArray.addAndGet(index0, delta);
        System.out.println("addAndGet result is:" + addAndGet);
        System.out.println("old array[" + index0 + "]" + "is:" + array[index0]);
        int index1 = 1;
        // 返回更新后（拷贝）数组下标为 index0 并且加上 delta 之前的值
        int getAndAdd = atomicIntegerArray.getAndAdd(index1, delta);
        System.out.println("getAndAdd result is:" + getAndAdd);
        System.out.println("old array[" + index1 + "]" + "is:" + array[index1]);
        int index2 = 2;
        // 返回更新（自减 1）后（拷贝）数组下标为 index2 的值
        int decrementAndGet = atomicIntegerArray.decrementAndGet(index2);
        System.out.println("decrementAndGet result is: " + decrementAndGet);
        System.out.println("old array[" + index2 + "]" + "is:" + array[index2]);
        int index3 = 3;
        // 返回更新（自增 1）后（拷贝）数组下标为 index3 的值
        int incrementAndGet = atomicIntegerArray.incrementAndGet(index3);
        System.out.println("incrementAndGet result is: " + incrementAndGet);
        System.out.println("old array[" + index3 + "]" + "is:" + array[index3]);
    }
}
