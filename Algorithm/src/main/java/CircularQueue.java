/**
 * @author heyou
 * @date 2023-07-23 15:52
 */
public class CircularQueue {
    // 数组
    private String[] items;
    // 数组大小
    private int n;

    // 队列头下标
    private int head;
    // 队列尾下表
    private int tail;
    // 初始化空间

    public CircularQueue (int size) {
        items = new String[size];
        n=size;
    }
    // 入队
    public boolean enqueue (String item) {
        // 判断队列是否满了
        if ((tail+1) %n == head) {
            return false;
        }
        items[tail] = item;
        tail = (tail+1)%n;
        return true;
    }
    // 出队
    public String dequeue () {
        // 判断队列是否为空
        if (head == tail) {
            return null;
        }
        String item = items[head];
        head = (head+1)%n;
        return item;
    }

    public static void main(String[] args) {
        double i = 5;
        int n = 5;
        double j = n;

        System.out.println(i/2/2+1);
    }
}
