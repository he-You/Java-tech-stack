import java.util.Iterator;

/**
 * 下压栈的链表实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/10/7 22:32
 */
public class Stack<Item> implements Iterable<Item> {
    private Node first;
    private int N;

    // 定义链表节点
    private class Node{
        Item item;
        Node next;
    }
    public boolean isEmpty(){
        return first == null;
    }
    public int size(){
        return N;
    }

    public void push(Item item){
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    public Item pop(){
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node curretnt = first;
        @Override
        public boolean hasNext() {
            return curretnt != null;
        }

        @Override
        public Item next() {
            Item item = curretnt.item;
            curretnt = curretnt.next;
            return item;
        }

        @Override
        public void remove() {

        }
    }
}
