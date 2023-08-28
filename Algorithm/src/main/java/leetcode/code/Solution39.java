package leetcode.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author heyou
 * @date 2023-08-28 14:44
 */
public class Solution39 {
    private List<List<Integer>> result = new ArrayList<>();
    private LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtracking(candidates, target, 0, 0);
        return result;
    }

    public void backtracking(int[] candidates, int target, int sum, int startIndex) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            result.add(new ArrayList(path));
            return;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            // 如果 sum + candidates[i] > target 就终止遍历
            if (sum + candidates[i] > target) {
                break;
            }
            path.add(candidates[i]);
            backtracking(candidates, target, sum + candidates[i], i);
            // 回溯，移除路径 path 最后一个元素
            path.removeLast();
        }
    }

    public static void main(String[] args) {
        Solution39 solution39 = new Solution39();
        List<List<Integer>> lists = solution39.combinationSum(new int[]{2, 3, 6, 7}, 7);
        System.out.println("");
    }
}
