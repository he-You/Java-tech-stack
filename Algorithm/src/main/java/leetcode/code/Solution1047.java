package leetcode.code;

import java.util.Stack;

/**
 * @author heyou
 * @date 2023-08-20 17:00
 */
public class Solution1047 {
    public static String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (!stack.isEmpty()) {
                if (c != stack.peek()) {
                    stack.push(c);
                } else {
                    stack.pop();
                }
            } else {
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop().toString());
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicates("abbaca"));
    }
}
