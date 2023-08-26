package leetcode.code;

import lombok.Data;

/**
 * @author heyou
 * @date 2023-08-25 15:30
 */
public class Solution236 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        // 后续遍历
        if (root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode leftResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right, p, q);
        if (leftResult != null && rightResult != null) {
            return root;
        }
        return null;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode();
        treeNode.setVal(3);
        TreeNode left = new TreeNode();
        left.setVal(9);
        left.setLeft(null);
        left.setRight(null);
        treeNode.setLeft(left);

        TreeNode right = new TreeNode();
        right.setVal(20);
        TreeNode rightChild = new TreeNode();
        rightChild.setVal(15);
        rightChild.setLeft(null);
        rightChild.setRight(null);
        right.setRight(rightChild);
        TreeNode leftChild = new TreeNode();
        leftChild.setVal(7);
        leftChild.setLeft(null);
        leftChild.setRight(null);
        right.setLeft(leftChild);
        treeNode.setRight(right);
        TreeNode res = lowestCommonAncestor(treeNode, new TreeNode(9), new TreeNode(7));
        System.out.println("");
    }
}
