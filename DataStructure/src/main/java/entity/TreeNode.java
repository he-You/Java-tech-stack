package entity;

/**
 * 树节点
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/7/4 18:18
 */
public class TreeNode {
    private int data;

    private TreeNode leftChild;

    private TreeNode rightChild;

    public TreeNode(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
}
