package leetcode.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-29 12:02
 */
public class Solution491 {
    private List<List<Integer>> result = new ArrayList<>();
    private LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        backtracking(nums, 0);
        return result;
    }

    public void backtracking (int[] nums, int startIndex) {
        if (path.size() >= 2) {
            result.add(new ArrayList<>(path));
            if (startIndex >= nums.length) {
                return;
            }
        }
        for (int i = startIndex; i < nums.length; i++) {
            if (i > 0 && nums[i] < nums[i-1]) {
                continue;
            }
            path.add(nums[i]);
            backtracking(nums, i+1);
            path.removeLast();
        }
    }

    public static void main(String[] args) {
        Solution491 solution491 = new Solution491();
        List<List<Integer>> subsequences = solution491.findSubsequences(new int[]{4, 6, 7, 7});
        System.out.println("");
    }
}
