package tree;

import lombok.Data;

/**
 * 二叉搜索树：节点左子树上左右的节点都小于父节点，右子树上所有的节点都大于父节点
 *
 * @author heyou
 * @date 2023-08-02 09:30
 */
public class BinarySearchTree {
    @Data
    static class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private static TreeNode tree;

    // 查询
    public static TreeNode search (int data, TreeNode root) {
        if (data == root.val) {
            return root;
        }
        // 去右子树找
        if (data > root.val) {
            return search(data, root.right);
        } else {
            return search(data, root.left);
        }
    }
    // 插入
    public static boolean insert (TreeNode root, TreeNode newNode) {
        if (root == null) {
            root = newNode;
            return true;
        }
        // 判断新节点的大小
        if (newNode.val > root.val) {
            // 往右子树插入
            if (root.right == null) {
                root.right = newNode;
                return true;
            } else {
                // 判断右子节点与当前插入的节点大小，如果当前节点大于

                return insert(root.right, newNode);
            }
        }
        if (newNode.val < root.val) {
            // 往左子树插入
            // 往右子树插入
            if (root.left == null) {
                root.left = newNode;
                return true;
            } else {
                return insert(root.left, newNode);
            }
        }
        return false;
    }

    public static void insert(int data) {
        if (tree == null) {
            tree = new TreeNode(data);
            return;
        }

        TreeNode p = tree;
        while (p != null) {
            if (data > p.val) {
                if (p.right == null) {
                    p.right = new TreeNode(data);
                    return;
                }
                p = p.right;
            } else { // data < p.data
                if (p.left == null) {
                    p.left = new TreeNode(data);
                    return;
                }
                p = p.left;
            }
        }
    }
    // 删除
//    public boolean delete (TreeNode root, TreeNode targetNode) {
//
//    }

    public static void main(String[] args) {
        int[] arr = new int[] {1,2,4,5,6,7,8};
        tree = new TreeNode(5);
        for (int i = 0; i< arr.length;i++) {
            insert(arr[i]);
        }
        System.out.println(tree);

    }


}
