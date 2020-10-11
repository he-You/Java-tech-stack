import java.util.Iterator;

/**
 * 背包的实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/10/11 22:47
 */
public class Bag<Item> implements Iterable<Item> {
    private Node first;

    private class Node{
        Item item;
        Node next;
    }

    private void add(Item item){
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {

        }
    }
}
