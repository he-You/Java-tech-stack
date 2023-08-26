package leetcode.code;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heyou
 * @date 2023-08-25 13:37
 */
public class Solution501 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() { }
        TreeNode(int val) { this.val = val; }
    }
    public static int[] findMode(TreeNode root) {
        List<Integer> res = inorderTravel(root);
        Map<Integer, Integer> map  = new HashMap<>();
        for (int i = 0; i < res.size(); i++) {
            map.put(res.get(i), map.getOrDefault(res.get(i), 0) + 1);
        }
        int maxValueCount = Integer.MIN_VALUE;
        List<Integer> maxKeyList = new ArrayList<>();
        // 找出众数的个数
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            maxValueCount = Math.max(maxValueCount, entry.getValue());
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxValueCount) {
                maxKeyList.add(entry.getKey());
            }
        }
        int[] resArray = new int[maxKeyList.size()];
        for (int i = 0; i < maxKeyList.size(); i++) {
            resArray[i] = maxKeyList.get(i);
        }
        return resArray;
    }

    public static List<Integer> inorderTravel (TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // 拿到中序遍历数组
        if (root == null) {
            return res;
        }
        res.addAll(inorderTravel(root.left));
        res.add(root.val);
        res.addAll(inorderTravel(root.right));
        return res;
    }

    public static void main(String[] args) {
//        TreeNode treeNode = new TreeNode();
//        treeNode.setVal(5);
//
//        TreeNode leftChild = new TreeNode();
//
//        TreeNode leftChildChild = new TreeNode();
//        leftChildChild.setVal(2);
//        leftChildChild.setLeft(null);
//        leftChildChild.setRight(null);
//
//        leftChild.setVal(2);
//        leftChild.setLeft(leftChildChild);
//        leftChild.setRight(null);
//        treeNode.setLeft(leftChild);
//
//
//        TreeNode rightChild = new TreeNode();
//        TreeNode rightChildChild = new TreeNode();
//        rightChildChild.setVal(6);
//        rightChildChild.setLeft(null);
//        rightChildChild.setRight(null);
//        rightChild.setVal(6);
//        rightChild.setLeft(rightChildChild);
//        rightChild.setRight(null);
//        treeNode.setRight(rightChild);
//        System.out.println(findMode(treeNode));
        TreeNode treeNode = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        TreeNode rightChild = new TreeNode(2);
        right.setLeft(rightChild);
        treeNode.setRight(right);
        System.out.println(findMode(treeNode));
    }
}
