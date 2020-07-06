package function;

import java.util.Arrays;

/**
 * 优先队列的实及相关操作
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/7/6 22:30
 */
public class PriorityQueue {
    private int size;

    private int[] array;

    public PriorityQueue() {
        array = new int[32];
    }

    private void resize() {
        // 扩容
        int newSize = this.size * 2;
        this.array = Arrays.copyOf(this.array, newSize);
    }

    /**
     * 入队
     *
     * @param key 入队元素
     */
    public void enQueue(int key) {
        // 判断是否扩容
        if (size >= array.length) {
            resize();
        }
        array[size++] = key;
        upAdjust();
    }

    /**
     * 出队
     *
     * @return 出队的元素
     * @throws Exception
     */
    public int deQueue() throws Exception {
        if (size <= 0) {
            throw new Exception("this queue is empty !");
        }
        // 获取堆顶的元素
        int head = array[0];
        // 让最后一个元素移动到堆顶
        array[0] = array[--size];
        downAdjust();
        return head;
    }

    /**
     * 上浮操作
     */
    private void upAdjust() {
        int childIndex = size - 1;
        int parentIndex = (childIndex - 1) / 2;
        // temp保持传入的叶子节点的值，用于最后的赋值
        int temp = array[childIndex];
        while (childIndex > 0 && temp > array[parentIndex]) {
            // 单向赋值，非交换操作
            array[childIndex] = array[parentIndex];
            childIndex = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        array[childIndex] = temp;
    }

    /**
     * 下沉
     */
    private void downAdjust() {
        // temp 保存父节点值，用于最后的赋值
        int parentIndex = 0;
        int temp = array[parentIndex];
        int childIndex = 2 * parentIndex + 1;
        while (childIndex < size) {
            // 如果有右孩子，且右孩子小于左孩子的值，则定位到右孩子
            if (childIndex + 1 < size && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 如果父节点小于任何一个孩子的值，则直接跳出
            if (temp >= array[childIndex]) {
                break;
            }
            // 单向赋值
            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = 2 * childIndex + 1;
        }
        array[parentIndex] = temp;
    }

    public static void main(String[] args) throws Exception {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.enQueue(3);
        priorityQueue.enQueue(5);
        priorityQueue.enQueue(10);
        priorityQueue.enQueue(2);
        priorityQueue.enQueue(7);
        System.out.println("出队元素:" + priorityQueue.deQueue());
        System.out.println("出队元素:" + priorityQueue.deQueue());
    }
}
