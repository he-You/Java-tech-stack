package leetcode.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-28 11:04
 */
public class Solution216 {
    private LinkedList<Integer> path = new LinkedList<>();
    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> combinationSum3(int k, int n) {
        backtracking(k,n,1, 0);
        return result;
    }

    public void backtracking (int k, int n, int startIndex, int sum) {
        if (sum > n) {
            return;
        }
        if (path.size() == k && sum == n) {
            result.add(new ArrayList(path));
            return;
        }
        for (int i = startIndex; i <= 9 - (k - path.size()) + 1; i++) {
            path.add(i);
            sum+=i;
            backtracking(k, n, i+1, sum);
            sum-=i;
            path.removeLast();
        }
    }

    public int sum (List<Integer> path) {
        int sum = 0;
        for (Integer i : path) {
            sum+=i;
        }
        return sum;
    }

    public static void main(String[] args) {
        Solution216 solution216 = new Solution216();
        String s = "23";
        System.out.println(s.length());
        s.toCharArray();
        System.out.println(solution216.combinationSum3(3, 7));
    }
}
