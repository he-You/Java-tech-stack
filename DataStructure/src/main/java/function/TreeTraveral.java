package function;

import entity.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树遍历--递归方法
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/7/4 21:36
 */
public class TreeTraveral {
    /**
     * 创建二叉树
     *
     * @param inputList 输入序列
     * @return
     */
    public static TreeNode creatBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (CollectionUtils.isEmpty(inputList)) {
            return null;
        }
        Integer data = inputList.removeFirst();
        if (data != null) {
            node = new TreeNode(data);
            node.setLeftChild(creatBinaryTree(inputList));
            node.setRightChild(creatBinaryTree(inputList));
        }
        return node;
    }

    /**
     * 前序遍历
     *
     * @param node 二叉树节点
     */
    public static void preOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getData());
        preOrderTraveral(node.getLeftChild());
        preOrderTraveral(node.getRightChild());
    }

    /**
     * 中序遍历
     *
     * @param node 二叉树节点
     */
    public static void inOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraveral(node.getLeftChild());
        System.out.println(node.getData());
        inOrderTraveral(node.getRightChild());
    }

    /**
     * 后序遍历
     *
     * @param node 二叉树节点
     */
    public static void postOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderTraveral(node.getLeftChild());
        postOrderTraveral(node.getRightChild());
        System.out.println(node.getData());
    }

    /**
     * 层序遍历
     * @param root 二叉树根节点
     */
    public static void levelOrderTraveral(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (CollectionUtils.isNotEmpty(queue)) {
            TreeNode node = queue.poll();
            assert node != null;
            System.out.println(node.getData());
            if (node.getLeftChild() != null) {
                queue.offer(node.getLeftChild());
            }
            if (node.getRightChild() != null) {
                queue.offer(node.getRightChild());
            }
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> inputList = new LinkedList<>(Arrays.asList(
                3, 2, 9, null, null, 10, null, null, 8, null, 4));
        TreeNode treeNode = creatBinaryTree(inputList);
        System.out.println("前序遍历：");
        preOrderTraveral(treeNode);
        System.out.println("中序遍历：");
        inOrderTraveral(treeNode);
        System.out.println("后序遍历：");
        postOrderTraveral(treeNode);
        System.out.println("层序遍历：");
        levelOrderTraveral(treeNode);
    }
}
