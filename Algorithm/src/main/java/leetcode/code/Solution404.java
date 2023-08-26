package leetcode.code;

import lombok.Data;

/**
 * @author heyou
 * @date 2023-08-23 16:48
 */
public class Solution404 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    
    public static int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return  0;
        }
        int sumLeft = sumLeft(root, 0, true);
        int sumRight = sumLeft(root.right, 0, false);
        return sumLeft+sumRight;
    }

    public static int sumLeft (TreeNode root, int sum, boolean isLeft) {
        if (root == null) {
            return sum;
        }
        // 判断是否是叶子节点
        if (root.left == null && root.right == null && isLeft) {
            return sum + root.val;
        }
        return sumLeft(root.left, sum, true);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode();
        treeNode.setVal(1);
        TreeNode left = new TreeNode();
        left.setVal(2);
        left.setLeft(null);
        left.setRight(null);

        TreeNode leftChild = new TreeNode();
        leftChild.setVal(4);
        leftChild.setLeft(null);
        leftChild.setRight(null);
        left.setLeft(leftChild);

        TreeNode rightChild = new TreeNode();
        rightChild.setVal(5);
        rightChild.setLeft(null);
        rightChild.setRight(null);
        left.setRight(rightChild);


        treeNode.setLeft(left);

        TreeNode right = new TreeNode();
        right.setVal(3);
//        TreeNode rightChild = new TreeNode();
//        rightChild.setVal(7);
//        rightChild.setLeft(null);
//        rightChild.setRight(null);
//        right.setRight(rightChild);
//        TreeNode leftChild = new TreeNode();
//        leftChild.setVal(15);
//        leftChild.setLeft(null);
//        leftChild.setRight(null);
//        right.setLeft(leftChild);
        treeNode.setRight(right);

        System.out.println(sumOfLeftLeaves(treeNode));
    }
}
