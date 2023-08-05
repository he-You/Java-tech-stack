package leetcode.code;

import lombok.Data;

/**
 *
 * 反转链表II: https://leetcode.cn/problems/reverse-linked-list-ii/
 *
 * @author heyou
 * @date 2023-07-23 14:53
 */
public class Solution92 {
    @Data
    public static class ListNode {

        private int val;

        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 将left和right区间中的节点进行翻转
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return head;
        }
        // 1.定义虚拟节点
        ListNode dummy = new ListNode(0, head);
        // 2.定义pre指针,初始位置分别指向虚拟节点
        ListNode pre = dummy;
        // 3.找到左边界前的pre节点
        for (int i = 0; i < left-1; i++) {
            pre = pre.next;
        }
        // 4.定义cur指针，指向pre的后继节点
        ListNode cur = pre.next;
        // 5.开始对left~right区间内的节点进行反转
        for (int i = 0; i< right - left; i++) {
            // 定义cur的后继节点指针
            ListNode next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode listNode = initLinkList();
        //        ListNode node1 = reverseLinkList(listNode, null);
        //        ListNode reverse = reverse(listNode);
        ListNode listNode1 = reverseBetween(listNode, 2, 5);
        System.out.println(listNode1);
    }

    private static ListNode initLinkList () {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.setNext(listNode2);
        listNode2.setNext(listNode3);
        listNode3.setNext(listNode4);
        listNode4.setNext(listNode5);
        return listNode1;
    }
}
