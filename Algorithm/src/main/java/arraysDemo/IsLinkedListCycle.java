package arraysDemo;

/**
 * 判断链表是否出现闭环:
 * 双指针循环遍历链表，如果快的指针'追上'慢指针，则说明链表有环
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/10/17 16:24
 */
public class IsLinkedListCycle {
    public static boolean isCycle(Node head){
        Node p1 = head;
        Node p2 = head;
        while (p2!=null && p2.next!=null){
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1==p2) {
                return true;
            }
        }
        return false;
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
        Node node1 = new Node(5);
        Node node2 = new Node(3);
        Node node3 = new Node(7);
        Node node4 = new Node(2);
        Node node5 = new Node(6);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node2;

        System.out.println(isCycle(node1));

    }
}
