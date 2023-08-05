package linklist;

import lombok.Data;

/**
 * 节点
 * @author heyou
 * @date 2022-01-01 14:32
 */
public class SingleListNode {
    /**
     * 当前节点的值
     */
    private Integer value;

    /**
     * 后继节点
     */
    private SingleListNode next;

    public SingleListNode next() {
        return this.getNext();
    }

    public Integer value() {
        return this.getValue();
    }

    private Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    private SingleListNode getNext() {
        return next;
    }

    public void setNext(SingleListNode next) {
        this.next = next;
    }
}
