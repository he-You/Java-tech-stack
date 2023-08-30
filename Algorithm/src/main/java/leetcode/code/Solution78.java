package leetcode.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-29 10:15
 */
public class Solution78 {
    private List<List<Integer>> result = new ArrayList<>();
    private LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtracking(nums, 0);
        result.add(new ArrayList<>());
        return result;
    }

    public void backtracking (int[] nums, int startIndex) {
        if (startIndex > nums.length) {
            return;
        }
        if (!path.isEmpty()) {
            result.add(new ArrayList<>(path));
        }
        for (int i = startIndex; i < nums.length; i++) {
            path.add(nums[i]);
            backtracking(nums, startIndex + 1);
            path.removeLast();
        }
    }

    public static void main(String[] args) {
        Solution78 solution78 = new Solution78();
        List<List<Integer>> subsets = solution78.subsets(new int[]{1, 2, 3});
        System.out.println("");
    }
}
