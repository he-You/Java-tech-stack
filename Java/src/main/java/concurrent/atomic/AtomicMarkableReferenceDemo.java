package concurrent.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/6 下午4:53
 */
public class AtomicMarkableReferenceDemo {
    public static void main(String[] args) {
        // 实例化、取当前值和 mark 值
        final Boolean initialRef = null;
        boolean initialMark = false;
        final AtomicMarkableReference<Boolean> atomicMarkableReference = new AtomicMarkableReference<>(initialRef, initialMark);
        System.out.println("currentValue=" + atomicMarkableReference.getReference()
                + ", currentMark=" + atomicMarkableReference.isMarked());

        // compare and set
        final Boolean newReference1 = true;
        boolean newMark1 = true;
        final boolean compareAndSet = atomicMarkableReference.compareAndSet(initialRef, newReference1, initialMark, newMark1);
        System.out.println("currentValue=" + atomicMarkableReference.getReference()
                + ", currentMark=" + atomicMarkableReference.isMarked()
                + ", compareAndSet result is :" + compareAndSet);

        // 获取当前的值和当前的 mark 值
        boolean[] arr = new boolean[1];
        final Boolean currentValue = atomicMarkableReference.get(arr);
        final boolean currentMark = arr[0];
        System.out.println("currentValue=" + currentValue + ", currentMark=" + currentMark);

        // 单独设置 mark 值
        final boolean attemptMarkResult = atomicMarkableReference.attemptMark(newReference1, false);
        System.out.println("currentValue=" + atomicMarkableReference.getReference()
                + ", currentMark=" + atomicMarkableReference.isMarked()
                + ", attemptMarkResult is " + attemptMarkResult);

        // 重新设置当前值和 mark 值
        atomicMarkableReference.set(initialRef, initialMark);
        System.out.println("currentValue=" + atomicMarkableReference.getReference()
                + ", currentMark=" + atomicMarkableReference.isMarked());
    }
}
