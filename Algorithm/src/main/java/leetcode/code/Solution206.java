package leetcode.code;

import lombok.Data;

import java.util.Stack;

/**
 * 翻转链表
 *
 * @author heyou
 * @date 2022-02-27 17:43
 */
public class Solution206 {

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

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        ListNode reverse = reverseList(head.getNext());
        head.getNext().getNext().setVal(head.getVal());
        head.getNext().setNext(null);
        return reverse;
    }

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

//        ListNode listNode = reverseList(node1);
//        ListNode listNode = reverse1(node1, null);
//        ListNode listNode2 = reverse2(node1);
        boolean palindrome = isPalindrome(node1);
        //        System.out.println(listNode);
        System.out.println(palindrome);
    }

    /**
     * 递归方式-反转链表
     *
     * @param cur 当前节点
     * @param pre 前驱节点
     * @return 反转后的节点
     */
    private static ListNode reverse1 (ListNode cur, ListNode pre) {
        // 定义递归结束条件
        if (cur == null) {
            return pre;
        }
        ListNode res = reverse1(cur.next, cur);
        cur.next = pre;
        return res;
    }

    /**
     * 双指针方式
     *
     * @param head 头节点
     * @return
     */
    private static ListNode reverse2 (ListNode head) {
        // 定义cur和pre指针
        ListNode cur = head, pre = null;
        while (cur!=null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    public static boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) {
            return true;
        }
        Stack<ListNode> nodeStack = new Stack<>();
        ListNode cur = head;
        while (cur!=null) {
            nodeStack.push(cur);
            cur = cur.next;
        }
        // 出栈
        ListNode cur2 = head;
        while (cur2 !=null) {
            if (cur2.val != nodeStack.pop().val) {
                return false;
            }
            cur2 = cur2.next;
        }
        return true;
    }

    public static boolean check(ListNode head, ListNode temp){
        if(head == null) {
            return true;
        }
        boolean res = check(head.next, temp) && temp.val == head.val;
        temp = temp.next;
        return res;
    }
}
