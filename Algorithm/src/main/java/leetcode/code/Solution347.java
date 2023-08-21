package leetcode.code;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 *
 * @author heyou
 * @date 2023-08-21 13:18
 */
public class Solution347 {
    public static int[] topKFrequent(int[] nums, int k) {
        // 先将数组中的元素放入map 中
        // key为数组元素值,val为对应出现次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        //在优先队列中存储二元组(num,cnt),cnt表示元素值num在数组中的出现次数
        // 创建了一个优先队列 priorityQueue，其中元素的类型为 int[]，即整型数组，
        // 使用了 Java 8 中的 lambda 表达式，通过 Comparator.comparingInt(pair -> pair[1]) 来创建一个比较器，该比较器根据数组中索引为 1 的元素进行比较。
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((pair1, pair2)->pair2[1]-pair1[1]);
        // 小顶堆只需要维持k个元素有序
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            priorityQueue.add(new int[]{entry.getKey(), entry.getValue()});
        }
        int[] ans = new int[k];
        //依次弹出小顶堆,先弹出的是堆的根,出现次数少,后面弹出的出现次数多
        for (int i = k - 1; i >= 0; i--) {
            ans[i] = priorityQueue.poll()[0];
        }
        return ans;
    }

    public static void main(String[] args) {
        topKFrequent(new int[] {1,1,1,2,2,3}, 2);
    }
}
