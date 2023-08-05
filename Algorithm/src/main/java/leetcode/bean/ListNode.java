package leetcode.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author heyou
 * @date 2022-02-27 17:41
 */
@Data
@Builder
public class ListNode {

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
