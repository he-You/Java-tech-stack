package linklist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 单链表
 *
 * @author heyou
 * @date 2022-01-01 14:31
 */
public class SingleLinkList {
    /**
     * 构建链表: 5->7->3->15->8->1
     *
     * @return 单链表头节点
     */
    private static SingleListNode init () {
        SingleListNode node1 = new SingleListNode();
        SingleListNode node2 = new SingleListNode();
        SingleListNode node3 = new SingleListNode();
        SingleListNode node4 = new SingleListNode();
        SingleListNode node5 = new SingleListNode();
        SingleListNode node6 = new SingleListNode();
        node1.setValue(5);
        node2.setValue(7);
        node3.setValue(3);
        node4.setValue(15);
        node5.setValue(8);
        node6.setValue(1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        node6.setNext(null);
        return node1;
    }

    /**
     * 插入
     *
     * @param currentNode 原单链表
     * @param newNode 新节点
     * @param targetValue 目标节点
     */
    private static void add (SingleListNode currentNode, SingleListNode newNode, Integer targetValue) {
        if (currentNode == null) {
            currentNode = newNode;
        }
        // 遍历单链表
        if (currentNode.value().equals(targetValue)) {
            newNode.setNext(currentNode.next());
            currentNode.setNext(newNode);
        } else {
            SingleListNode nextNode = currentNode.next();
            add(nextNode, newNode, targetValue);
        }
    }

    /**
     * 删除链表中的节点:
     * 1.遍历节点
     * 2.找到目标节点的前驱节点和后继节点
     * 3.将前驱节点的后继指向当前节点的后继，将当前节点的后继指向null
     *
     * @param targetValue 目标节点值
     */
    private static SingleListNode delete (SingleListNode preNode, SingleListNode curretNode, Integer targetValue) {
        // 如果删除的头节点
        if (preNode == null) {
            if (targetValue.equals(curretNode.value())) {
                curretNode = curretNode.next();
                return curretNode;
            } else {
               return delete(curretNode, curretNode.next(), targetValue);
            }
        } else {
            // 删除其他节点
            SingleListNode nextNode = curretNode.next();
            if (targetValue.equals(curretNode.value())) {
                preNode.setNext(curretNode.next());
                curretNode.setNext(null);
                return preNode;
            } else {
               return delete(curretNode, nextNode, targetValue);
            }
        }
    }

    /**
     * 递归打印
     *
     * @param currentNode 当前节点
     */
    private static void printValue (SingleListNode currentNode) {
        System.out.print(currentNode.value()+"->");
        if (currentNode.next() != null) {
            printValue(currentNode.next());
        }
    }

    public static void main(String[] args) {
        SingleListNode singleList = init();
        printValue(singleList);
        System.out.println();
        // 新增node 9
        SingleListNode newNode = new SingleListNode();
        newNode.setValue(9);
        add(singleList, newNode, 15);
        System.out.println("新链表:");
        printValue(singleList);
        System.out.println();
        // 删除
        Integer targetValue = 15;
        SingleListNode newSingleList = delete(null, singleList, targetValue);
        System.out.println("删除后的链表:");
        printValue(newSingleList);
        System.out.println();
    }

    class Node {
        int val;
        Node next;
        Node(int val) {
            this.val = val;
        }
    }
    int size;
    Node head;

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 || index >= size || head == null) {
            return -1;
        }
        Node temp = this.head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node node = new Node(val);
        node.next = this.head;
        this.head = node;
        size++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if (size == 0) {
            this.head = new Node(val);
            head.next = null;
            size++;
        }else {
            Node temp = this.head;
            while (temp.next != null) {
                temp = temp.next;
            }
            Node tail = new Node(val);
            tail.next = null;
            temp.next = tail;
            size++;
        }
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index > this.size) {
            return;
        }
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        if (index == this.size) {
            addAtTail(val);
            return;
        }

        Node temp = this.head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        Node insertNode = new Node(val);
        insertNode.next = temp.next;
        temp.next = insertNode;
        size++;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= this.size) {
            return;
        }
        if (index == 0) {
            if (size != 1) {
                Node temp = this.head.next;
                this.head =temp;
                size--;
                return;
            }else {
                this.head = null;
                size--;
                return;
            }
        }
        Node temp = this.head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        Node deleteNode = temp.next;
        temp.next = deleteNode.next;
        size--;
    }

}
