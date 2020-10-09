import java.util.Iterator;

/**
 * 队列的实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/10/9 23:26
 */
public class Queue<Item> implements Iterable<Item> {
    /**
     * 定义最早添加的节点的链接
     */
    private Node first;

    /**
     * 指向最近添加的节点的链接
     */
    private Node last;
    /**
     * 队列的元素数量
     */
    private int N;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    /**
     * 入队
     * @param item 元素
     */
    public void enqueue(Item item){
        // 向链表的尾部添加元素
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        N++;
    }

    public Item dequeue(){
        // 从表头删除元素
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        N--;
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}
