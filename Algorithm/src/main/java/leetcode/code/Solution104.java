package leetcode.code;

import lombok.Data;

/**
 * @author heyou
 * @date 2023-08-02 17:00
 */
public class Solution104 {
    @Data
    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lDepth = maxDepth(root.left);
        int rDepth = maxDepth(root.right);
        return Math.max(lDepth, rDepth) +1;
    }
}
