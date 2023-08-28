package leetcode.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-28 12:09
 */
public class Solution17 {
    private List<String> result = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        // 定义对应的字符串数组，下标对应电话号码数字
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtracking(digits, numString, 0);
        return result;
    }

    //每次迭代获取一个字符串，所以会设计大量的字符串拼接，所以这里选择更为高效的 StringBuild
    StringBuilder temp = new StringBuilder();

    public void backtracking (String digits, String[] numString, int num) {
        //遍历全部一次记录一次得到的字符串
        if (num == digits.length()) {
            result.add(temp.toString());
            return;
        }
        //str 表示当前num对应的字符串
        String str = numString[getDigitStrIndex(digits, num)];
        for (int i = 0; i < str.length(); i++) {
            // 处理节点
            temp.append(str.charAt(i));
            // 回溯
            backtracking(digits, numString, num + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }

    public int getDigitStrIndex (String digits, int index) {
        return Integer.parseInt(String.valueOf(digits.charAt(index)));
    }

    public static void main(String[] args) {
        Solution17 solution17 = new Solution17();
        List<String> stringList = solution17.letterCombinations("23");
        System.out.println("");
    }
}
