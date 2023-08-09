package leetcode.code;

import lombok.Data;

/**
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 *
 * @author heyou
 * @date 2023-08-08 17:35
 */
public class Solution234 {
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

    public static boolean isPalindrome(ListNode head) {
        ListNode dummy = new ListNode(0,head);
        ListNode fast = dummy;
        ListNode slow = dummy;
        while (fast!=null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //
        ListNode mid = slow;
        ListNode right = mid.next;
        mid.next = null;
        // 反转right
        ListNode reverseRight= reverse(right, null);
        //
        ListNode l = head;
        ListNode r = reverseRight;
        boolean flag = true;
        while (r !=null) {
            if (l.val != r.val) {
                flag =false;
                break;
            }
            l = l.next;
            r = r.next;
        }
        return flag;
    }

    public static ListNode reverse (ListNode cur, ListNode pre) {
        if (cur == null) {
            return pre;
        }
        ListNode res = reverse(cur.next, cur);
        cur.next = pre;
        return res;
    }

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(4, node3);
        ListNode node1 = new ListNode(5, node2);

        //        ListNode listNode = reverseList(node1);
        //        ListNode listNode = reverse1(node1, null);
        //        ListNode listNode2 = reverse2(node1);
        boolean palindrome = isPalindrome(node1);
        //        System.out.println(listNode);
        System.out.println(palindrome);
    }
}
