package leetcode.code;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heyou
 * @date 2023-08-24 23:55
 */
public class Solution654 {
    @Data
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }

    private static Map<Integer, Integer> map = new HashMap<>();
    // 最大值作为root,根据root所在的位置将左右分割成两个部分
    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        return buildTree(nums, 0, nums.length);
    }

    public static TreeNode buildTree (int[] nums, int begin, int end) {
        if (begin >= end) {
            return null;
        }
        int max = 0;
        for (int i = begin; i < end; i++) {
            max = Math.max(nums[i], max);
        }
        TreeNode root = new TreeNode(max);
        int maxValueIndex = map.get(max);
        root.left = buildTree(nums, begin, maxValueIndex);
        root.right = buildTree(nums, maxValueIndex + 1, end);
        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode = constructMaximumBinaryTree(new int[]{3, 2, 1, 6, 0, 5});
        System.out.println("");
    }
}
