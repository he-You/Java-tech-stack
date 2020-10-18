package arraysDemo;

/**
 * 判断链表是否有环，如果有计算环的长度
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/10/17 16:58
 */
public class LinkedListCycleLength {

    public static int isCycle(Node head){
        Node p1 = head;
        Node p2 = head;
        while (p2!=null && p2.next!=null){
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1==p2) {
                // 第一次相遇
                return caculateCycleLength(p2,p1);
            }
        }
        // 没有环
        return 0;
    }

    private static int caculateCycleLength(Node fast,Node slow){
        int length = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast==slow) {
                // 第一次相遇
                length = length+1;
                return length;
            }
            length++;
        }
        return length;
    }

    /**
     * 链表节点
     */
    private static class Node{
        int data;
        Node next;
        Node(int data){
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(4);
        Node node3 = new Node(5);
        Node node4 = new Node(8);
        Node node5 = new Node(3);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node4;

        System.out.println(isCycle(node1));

    }
}
