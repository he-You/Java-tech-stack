import java.util.Iterator;

/**
 * 下压栈
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/9/27 23:43
 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    /**
     * 栈元素
     */

    private Item[] a = (Item[]) new Object[1];
    /**
     * 初始元素数量
     */
    private int capacity = 0;

    private boolean isEmpty() {
        return capacity == 0;
    }

    private int size() {
        return capacity;
    }

    /**
     * 扩容
     *
     * @param max 元素个数
     */
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < capacity; i++) {
            temp[i] = a[i];
            a = temp;
        }
    }

    public Item pop() {
        Item item = a[--capacity];
        // 处理游离元素
        a[capacity] = null;
        if (capacity > 0 && capacity == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = capacity;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return a[--i];
        }

        @Override
        public void remove() {

        }
    }
}
