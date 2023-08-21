package leetcode.code;

import lombok.Data;

/**
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 *
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 *
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * @author heyou
 * @date 2023-08-19 23:46
 */
public class Solution2 {


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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode cur = new ListNode(0);
        sum(cur, l1, l2, false);
        return cur;
    }

    public static ListNode sum (ListNode cur, ListNode l1, ListNode l2, boolean isCarry) {
        if (l1 == null && l2 == null && !isCarry) {
            return cur;
        }
        if (l1 == null) {
            l1 = new ListNode(0);
        }
        if (l2 == null) {
            l2 = new ListNode(0);
        }
        int carry = 0;
        if (isCarry) {
            carry = 1;
        }
        if (l1.val + l2.val + carry >= 10) {
            isCarry = true;
            cur.val= (l1.val+l2.val + carry) % 10;
        } else {
            isCarry = false;
            cur.val = l1.val+l2.val + carry;
        }
        // 判断是否要向后计算
        if (isCarry) {
            cur.next = new ListNode(0);
            return sum(cur.next, l1.next, l2.next, isCarry);
        }
        if (l1.next != null || l2.next != null) {
            cur.next = new ListNode(0);
            return sum(cur.next, l1.next, l2.next, isCarry);
        }
        return cur;
    }

    public static void main(String[] args) {
//        ListNode node15 = new ListNode(9, null);
//        ListNode node14 = new ListNode(9, node15);
//        ListNode node13 = new ListNode(9, node14);
        ListNode node12 = new ListNode(8, null);
        ListNode node11 = new ListNode(1, node12);

//        ListNode node23 = new ListNode(9, null);
//        ListNode node22 = new ListNode(9, node23);
        ListNode node21 = new ListNode(0, null);
        addTwoNumbers(node11, node21);

    }
}
