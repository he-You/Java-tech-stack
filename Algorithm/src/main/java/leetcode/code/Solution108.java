package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-27 11:46
 */
public class Solution108 {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        // 找到数组的中点，作为树的根节点
        return build(nums, 0, nums.length -1);
    }

    public static TreeNode build(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(nums, start, mid - 1);
        root.right = build(nums, mid + 1, end);
        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode = sortedArrayToBST(new int[]{-10, -3, 0, 5, 9});
        System.out.println(treeNode);
    }
}
