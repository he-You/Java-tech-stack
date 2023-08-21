package leetcode.code;

/**
 * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
 * <p>
 * 如果剩余字符少于 k 个，则将剩余字符全部反转。
 * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
 *
 * @author heyou
 * @date 2023-08-11 09:54
 */
public class Solution541 {
    public static String reverseStr(String s, int k) {
        // 定义双指针位置
        for (int i = 0; i < s.length(); i = (i + 2 * k)) {
            if (i + k <= s.length()) {
                // 翻转i到i+k之间的字符
                s = reverse(s, i, i + k-1);
            } else {
                // 3. 剩余字符少于 k 个，则将剩余字符全部反转。
                s = reverse(s, i, s.length()-1);
            }
        }
        return s;
    }


    public static String reverse(String s, int startIndex, int endIndex) {
        char[] c = s.toCharArray();
        int l = startIndex;
        int r = endIndex;
        while (l < r) {
            char tmp = c[l];
            c[l] = c[r];
            c[r] = tmp;
            l++;
            r--;
        }
        return String.valueOf(c);
    }

    public static void main(String[] args) {
        String s = "abcdefg";
        int k = 2;
        System.out.println(reverseStr(s, k));
    }
}
