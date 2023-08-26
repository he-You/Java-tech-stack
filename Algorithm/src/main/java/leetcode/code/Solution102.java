package leetcode.code;

import lombok.Data;
import tree.BinaryTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-22 10:34
 */
public class Solution102 {
    @Data
    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        // 将二叉树放入队列中
        // 当队列不为空时，取队头的元素，判断队头的元素是否有子节点，如果存在，将左右子节点依次放入队列中，直到队列为空
        // 定义队列
        Deque<TreeNode> dequeue = new LinkedList<>();
        dequeue.add(root);
        int size = dequeue.size();
        while (size > 0) {
            List<Integer> list = new ArrayList<>();
            // 每次循环size是固定的，所以不能直接用dequeue的size(),因为队列的长队是动态变化的
            for (int i = 0; i < size;i++) {
                TreeNode node = dequeue.poll();
                list.add(node.val);
                if (node.left != null) {
                    dequeue.add(node.left);
                }
                if (node.right != null) {
                    dequeue.add(node.right);
                }
            }
            if (!list.isEmpty()) {
                res.add(list);
            }
            size = dequeue.size();
        }
        Collections.reverse(res);
        return res;
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

        //        List<List<Integer>> lists = levelTravelsal(treeNode);
        List<List<Integer>> integers = levelOrder(treeNode);
        System.out.println(integers);
    }

}
