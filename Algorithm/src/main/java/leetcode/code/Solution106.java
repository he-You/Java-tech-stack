package leetcode.code;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heyou
 * @date 2023-08-24 15:17
 */
@Data
public class Solution106 {
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

    private static Map<Integer, Integer> inorderMap = new HashMap<>();

    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i< inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return createTree(inorder, 0, inorder.length,postorder, 0, postorder.length);
    }

    /**
     *
     * @param inorder 中序遍历数组
     * @param inBegin 数组的起始位置
     * @param inEnd 数组的长度
     * @param postorder 后序遍历数组
     * @param postBegin 数组的起始位置
     * @param postEnd 数组的长度
     * @return
     */
    public static TreeNode createTree (int[] inorder, int inBegin, int inEnd, int[] postorder, int postBegin, int postEnd) {
        if (postorder.length == 0 || inBegin >= inEnd || postBegin >= postEnd) {
            return null;
        }

        // 后续遍历找根节点
        int rootVal = postorder[postEnd - 1];
        int rootIndex = inorderMap.get(rootVal);
        TreeNode root = new TreeNode(rootVal);
        // 左子树的个数，用于确定左子树的起止位置截取后续遍历数组
        int leftTreeNodeCount = rootIndex - inBegin;

        root.left = createTree(inorder, inBegin, rootIndex, postorder, postBegin, postBegin + leftTreeNodeCount);

        root.right = createTree(inorder, rootIndex + 1, inEnd, postorder, postBegin + leftTreeNodeCount, postEnd-1);
        return root;
    }

    public TreeNode createNode (TreeNode root, int leftValue, int rightValue) {
        root.left = new TreeNode(leftValue);
        root.right = new TreeNode(rightValue);
        return root;
    }

    public static void main(String[] args) {
        int[] inorder = new int[]{9,3,15,20,7};
        int[] postorder = new int[]{9,15,7,20,3};
        TreeNode treeNode = buildTree(inorder, postorder);
        System.out.println("");
    }
}
