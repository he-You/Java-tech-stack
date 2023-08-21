package leetcode.code;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author heyou
 * @date 2023-08-20 21:10
 */
public class Solution239 {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 1) {
            return nums;
        }
        int[] result = new int[nums.length - k + 1];
        int num = 0;
        // 自定义队列
        MyQueue queue = new MyQueue();
        // 将前k个元素放入队列中
        for (int i = 0; i < k; i++) {
            queue.add(nums[i]);
        }
        result[num] = queue.peek();
        num++;
        for (int i = k; i< nums.length; i++) {
            //滑动窗口移除最前面的元素，移除是判断该元素是否放入队列
            queue.poll(nums[i - k]);
            //滑动窗口加入最后面的元素
            queue.add(nums[i]);
            //记录对应的最大值
            result[num] = queue.peek();
            num++;
        }
        return result;
    }

    public static void main(String[] args) {
        maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3);
//        maxSlidingWindow(new int[]{1}, 1);
    }
}

class MyQueue {
    Deque<Integer> deque = new LinkedList<>();
    //弹出元素时，比较当前要弹出的数值是否等于队列出口的数值，如果相等则弹出
    //同时判断队列当前是否为空
    void poll(int val) {
        if (!deque.isEmpty() && val == deque.peek()) {
            deque.poll();
        }
    }
    //添加元素时，如果要添加的元素大于入口处的元素，就将入口元素弹出
    //保证队列元素单调递减
    //比如此时队列元素3,1，2将要入队，比1大，所以1弹出，此时队列：3,2
    void add(int val) {
        while (!deque.isEmpty() && val > deque.getLast()) {
            deque.removeLast();
        }
        deque.add(val);
    }
    //队列队顶元素始终为最大值
    int peek() {
        return deque.peek();
    }
}
