package function;

/**
 * 循环队列的实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/7/9 22:31
 */
public class CircularQueue {
    private int[] array;

    private int front;

    private int rear;

    public CircularQueue() {
    }

    public CircularQueue(int capacity) {
        this.array = new int[capacity];
    }

    /**
     * 入队
     * @param element 入队的元素
     * @throws Exception 抛出异常
     */
    public void enQueue(int element) throws Exception{
        if((rear+1)%array.length == front){
            throw new Exception("队列已满");
        }
        array[rear] = element;
        rear = (rear + 1)%array.length;
    }

    /**
     * 出队
     * @return 出队元素
     * @throws Exception 抛出异常
     */
    public int deQueue() throws Exception{
        if(rear == front){
            throw new Exception("队列已空");
        }
        int deQueueElement = array[front];
        front = (front+1)%array.length;
        return deQueueElement;
    }

    /**
     * 输出队列
     */
    public void output(){
        for(int i = front;i!=rear;i=(i+1)%array.length){
            System.out.println(array[i]);
        }
    }

    public static void main(String[] args) throws Exception {
        CircularQueue circularQueue = new CircularQueue(6);
        circularQueue.enQueue(3);
        circularQueue.enQueue(5);
        circularQueue.enQueue(6);
        circularQueue.enQueue(8);
        circularQueue.enQueue(1);
        circularQueue.deQueue();
        circularQueue.deQueue();
        circularQueue.deQueue();
        circularQueue.enQueue(2);
        circularQueue.enQueue(4);
        circularQueue.enQueue(9);
        circularQueue.output();
    }
}
