package leetcode.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-28 15:15
 */
public class Solution40 {
    private List<List<Integer>> result = new ArrayList<>();
    private LinkedList<Integer> path = new LinkedList<>();
    private boolean[] used;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 先进行排序
        Arrays.sort(candidates);
        used = new boolean[candidates.length];
        for (int i = 0; i < candidates.length; i++) {
            used[i] = false;
        }
        backtracking(candidates, target, 0, 0);
        return result;
    }

    public void backtracking(int[] candidates, int target, int sum, int startIndex) {
        if (sum == target) {
            result.add(new ArrayList(path));
            return;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            if (sum + candidates[i] > target) {
                break;
            }
            // 出现重复节点，同层的第一个节点已经被访问过，所以直接跳过
            if (i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]) {
                continue;
            }
            used[i] = true;
            sum += candidates[i];
            path.add(candidates[i]);
            // 每个节点仅能选择一次，所以从下一位开始
            backtracking(candidates, target, sum, i + 1);
            used[i] = false;
            sum -= candidates[i];
            path.removeLast();
        }
    }

    public static void main(String[] args) {
        Solution40 solution40 = new Solution40();
        String  s = "abbba";
        System.out.println(s.substring(0, 1));
        System.out.println(s.substring(1));

        System.out.println(check(s));
//        List<List<Integer>> lists = solution40.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
//        System.out.println(lists);
    }

    public static boolean check (String str) {
        int j = str.length() - 1;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
            j--;
        }
        return true;
    }
}
