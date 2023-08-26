package leetcode.code;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-23 14:58
 */
public class Solution257 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        // 前序遍历：根左右
        List<String> res = new ArrayList<>();
        getPath(root, new ArrayList<>(), res);
        return res;
    }

    public static void getPath(TreeNode node, List<Integer> singlePath, List<String> resultPath) {
        singlePath.add(node.val);
        // 遍历到叶子节点就开始回溯
        if (node.left == null && node.right == null) {
             resultPath.add(appendVal(singlePath));
             return;
        }
        if (node.left != null) {
            getPath(node.left, singlePath, resultPath);
            // 回溯的过程：将列表最后一个元素移除
            singlePath.remove(singlePath.size()-1);
        }
        if (node.right != null) {
            getPath(node.right, singlePath, resultPath);
            // 回溯的过程：将列表最后一个元素移除
            singlePath.remove(singlePath.size()-1);
        }
    }

    private static String appendVal(List<Integer> vals) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.size(); i++) {
            if (i != vals.size() - 1) {
                sb.append(vals.get(i)).append("->");
            } else {
                sb.append(vals.get(i));
            }
        } return sb.toString();
    }

    public static void main(String[] args) {
        //        int[] arr = new int[]{2, 2, 3, 4, 4, 5, 5};
        //        int res = 0;
        //        for (int i = 0; i < arr.length; i++) {
        //            res = res ^ arr[i];
        //        }
        // [3,9,20,null,null,15,7]
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

        List<String> stringList = binaryTreePaths(treeNode);
        System.out.println();
    }
}
