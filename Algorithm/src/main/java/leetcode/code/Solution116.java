package leetcode.code;

import lombok.Data;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author heyou
 * @date 2023-08-22 17:20
 */
public class Solution116 {
    @Data
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;
    }

    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Deque<Node> queue = new LinkedList<>();
        queue.add(root);
        int size = queue.size();
        while (size > 0) {
            Node nodePre = null;
            Node cur;
            for (int i = 0; i < size; i++) {
                // 获取队头元素
                if (i == 0) {
                    // 初始化前置节点
                    nodePre = queue.poll();
                    cur = nodePre;
                } else {
                    // 当前队头节点
                    cur = queue.poll();
                    // 设置next指向
                    nodePre.next = cur;
                    // 更新当前节点的前置节点指针指向
                    nodePre = nodePre.next;
                }
                // 将下一层放入
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            size = queue.size();
        }
        return root;
    }
}
