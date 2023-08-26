package tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author heyou
 * @date 2023-07-29 21:01
 */
public class BinaryTree {
    @Data
    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;
    }

    /**
     * 前序遍历：根->左->右
     *
     * @param treeNode
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode treeNode) {

        List<Integer> result = new ArrayList<>();

        //退出条件
        //root节点为空
        if (null == treeNode) {
            return result;
        }

        //一步的逻辑
        //遍历根节点，左节点，右节点
        result.add(treeNode.val);
        result.addAll(preorderTraversal(treeNode.left));
        result.addAll(preorderTraversal(treeNode.right));
        return result;
    }

    /**
     * 中序遍历：左->根->右
     *
     * @param treeNode
     * @return
     */
    public List<Integer> midTraversal(TreeNode treeNode) {

        List<Integer> result = new ArrayList<>();

        //退出条件
        //root节点为空
        if (null == treeNode) {
            return result;
        }

        //一步的逻辑
        //遍历左节点，根节点，右节点
        result.addAll(midTraversal(treeNode.left));
        result.add(treeNode.val);
        result.addAll(midTraversal(treeNode.right));
        return result;
    }

    /**
     * 中序遍历：左->根->右
     *
     * @param treeNode
     * @return
     */
    public static List<Integer> midTraversal2(TreeNode root) {

        List<Integer> result = new ArrayList<>();
        //退出条件
        //root节点为空
        if (null == root) {
            return result;
        }
        // 定义栈
        Stack<TreeNode> stack = new Stack<>();
        // 定义遍历指针
        TreeNode cur = root;
        // 开启循环
        while (cur != null || !stack.isEmpty()) {
            // 遍历
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                //
                cur = stack.pop();
                result.add(cur.val);
                cur = cur.right;
            }
        }
        return result;
    }

    /**
     * 后序遍历：左->右->根
     *
     * @param treeNode
     * @return
     */
    public List<Integer> afterTraversal(TreeNode treeNode) {

        List<Integer> result = new ArrayList<>();

        //退出条件
        //root节点为空
        if (null == treeNode) {
            return result;
        }

        //一步的逻辑
        //遍历左节点，右节点，根节点
        result.addAll(afterTraversal(treeNode.left));
        result.addAll(afterTraversal(treeNode.right));
        result.add(treeNode.val);
        return result;
    }

    /**
     * 层序遍历：先将根节点放入队列中，然后将根节点的左右子节点放入队列
     *
     * @param treeNode
     * @return
     */
    public static List<List<Integer>> levelTravelsal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        // 待遍历的层级节点列表
        LinkedList<TreeNode> treeNodes = new LinkedList<>();
        if (root != null) {
            treeNodes.add(root);
        }
        int size = treeNodes.size();
        while (size > 0) {
            // 创建保存当前层序的结果集列表
            List<Integer> item = new ArrayList<>();
            // 遍历当前层序，加入结果集及获取下一层级的节点
            for (int i = 0; i < size; i++) {
                // 获取队列头节点
                TreeNode node = treeNodes.remove();
                item.add(node.val);
                if (node.left != null) {
                    treeNodes.add(node.left);
                }
                if (node.right != null) {
                    treeNodes.add(node.right);
                }
            }
            size = treeNodes.size();
            result.add(item);
        }
        return result;
    }

    /**
     * 出队
     *
     * @return
     */
    private void dequeue(TreeNode treeNode, List<Integer> res) {
        if (treeNode == null) {
            return;
        }
        res.add(treeNode.val);
    }

    /**
     * 出队
     *
     * @return
     */
    private void enqueue(TreeNode treeNode, LinkedList<TreeNode> queue) {
        queue.add(treeNode);
    }

    /**
     * 判断是否存在路径，路径中节点的和等于targetSum
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }


    /**
     * 判断是否是对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return checkIsSymmetric(root.left, root.right);
    }

    public boolean checkIsSymmetric (TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null)  {
            return false;
        }
        if (left.val == right.val) {
            return true;
        }
        return checkIsSymmetric(left.left, right.right) && checkIsSymmetric(left.right, right.left);
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
        List<Integer> integers = midTraversal2(treeNode);
        System.out.println(integers);
    }

}
