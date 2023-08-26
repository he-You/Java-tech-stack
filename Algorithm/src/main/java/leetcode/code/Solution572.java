package leetcode.code;

import lombok.Data;

/**
 * @author heyou
 * @date 2023-08-23 16:25
 */
public class Solution572 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // 前序遍历root,当存在root.val == subRoot.val时，比对是否相同
        if (isSame(root, subRoot)|| subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        boolean leftResult = isSubtree(root.left, subRoot);
        boolean rightResult = isSubtree(root.right, subRoot);
        return leftResult || rightResult;
    }

    public static boolean isSame(TreeNode root, TreeNode subRoot){
        if (root == null && subRoot == null) {
            return true;
        }
        if (root == null || subRoot == null) {
            return false;
        }
        if (root.val != subRoot.val) {
            return false;
        }
        return isSame (root.left, subRoot.left) && isSame (root.right, subRoot.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode();
        root.setVal(1);
        TreeNode rootleft = new TreeNode();
        rootleft.setVal(1);
        root.setLeft(rootleft);
        TreeNode subRoot = new TreeNode();
        subRoot.setVal(1);
        System.out.println(isSubtree(root, subRoot));
    }
}
