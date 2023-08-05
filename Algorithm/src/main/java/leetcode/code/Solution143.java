package leetcode.code;

import lombok.Data;

import java.util.List;

/**
 * @author heyou
 * @date 2023-07-31 21:39
 */
public class Solution143 {
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

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        // 找出链表中点
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 反转后半部分链表
        ListNode mid = slow;
        // 反转
        ListNode right = reverse(mid.next, null);
        // 断开原始链表
        slow.next = null;
        ListNode cur = head;
        ListNode tmp = right;
        // 将后半部分的链表插入到前半部分中
        while (cur != null && tmp != null){
            ListNode lNext = cur.next;
            ListNode rNext = tmp.next;
            tmp.next = cur.next;
            cur.next = tmp;
            tmp = rNext;
            cur = lNext;
        }
        System.out.println(head);
    }

    private static ListNode reverse(ListNode cur, ListNode pre) {
        if (cur == null) {
            return pre;
        }
        ListNode res = reverse(cur.next, cur);
        cur.next = pre;
        return res;
    }

    public static void main(String[] args) {
        ListNode listNode = initLinkList();
        reorderList(listNode);
    }

    private static ListNode initLinkList() {
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
