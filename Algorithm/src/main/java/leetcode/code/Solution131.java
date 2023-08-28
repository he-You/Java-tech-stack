package leetcode.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-28 16:46
 */
public class Solution131 {
    private List<List<String>> result = new ArrayList<>();
    private LinkedList<String> path = new LinkedList<>();

    public List<List<String>> partition(String s) {
        backtracking(s, 0);
        return result;
    }

    public void backtracking (String s, int startIndex) {
        if (startIndex == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i < s.length(); i++) {
            //如果是回文子串，则记录
            if (isPalindrome(s, startIndex, i)) {
                String str = s.substring(startIndex, i + 1);
                path.add(str);
            } else {
                continue;
            }
            //起始位置后移，保证不重复
            backtracking(s, i + 1);
            path.removeLast();
        }
    }

    public boolean isPalindrome (String str, int startIndex, int end) {
        for (int i = startIndex, j = end; i < j; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution131 solution131 = new Solution131();
        StringBuilder sb = new StringBuilder();
        sb.append("a").append("b");
        System.out.println(sb.toString());
        sb.delete(1,2);
        System.out.println(sb.toString());
//        List<List<String>> aab = solution131.partition("aab");
        System.out.println("");
    }
}
