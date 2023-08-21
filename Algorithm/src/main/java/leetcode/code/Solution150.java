package leetcode.code;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author heyou
 * @date 2023-08-20 20:44
 */
public class Solution150 {
    public static int evalRPN(List<String> tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String item : tokens) {
            if (!isOperator(item)) {
                stack.push(Integer.parseInt(item));
            } else {
                // 获取栈顶两个元素
                int one = stack.pop();
                int two = stack.pop();
                int result = calculate(one,two, item);
                stack.push(result);
            }
        }
        return stack.peek();
    }

    private static boolean isOperator (String item) {
        return "+".equals(item) || "-".equals(item) || "*".equals(item) || "/".equals(item);
    }

    private static int calculate (int one, int two, String operator) {
        if ("+".equals(operator)) {
            return one + two;
        }
        if ("-".equals(operator)) {
            return two - one;
        }
        if ("*".equals(operator)) {
            return one * two;
        }
        return two / one;
    }

    public static void main(String[] args) {
        evalRPN(Arrays.asList("4","13","5","/","+"));
    }
}
