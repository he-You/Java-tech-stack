package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-11 17:42
 */
public class Solution28 {
    public static int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        int[] next = new int[needle.length()];

        getNext(next, needle);
        // 定义模式串的游标
        int j = 0;
        // i为主串的游标
        for (int i = 0; i < haystack.length(); i++) {
            // 持续的进行比对，如果模式串和主串的字符不相同，根据next数据中的数据，找到模式串中游标下一次比对的起始位置
            while (j > 0 && needle.charAt(j) != haystack.charAt(i)) {
                j = next[j - 1];
            }
            // 如果两个游标指向的字符相同，则i和j同时向后移动
            if (needle.charAt(j) == haystack.charAt(i)) {
                j++;
            }
            // 当游标指向模式串末尾时，返回主串游标的下标
            if (j == needle.length()) {
                return i - needle.length() + 1;
            }
        }
        return -1;
    }

    public static void getNext (int[] next, String needle) {
        char[] c = needle.toCharArray();
        // 1.初始化
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < c.length; i++) {
            // 当前缀和后缀不相同时，j向前移动
            while (j > 0 && c[i] != c[j]) {
                j = next[j-1];
            }
            if (c[i]==c[j]) {
                j++;
            }
            next[i] = j;
        }
    }

    public static void main(String[] args) {
        String haystack = "abeababeabf", needle = "ababea";
        System.out.println(strStr(haystack, needle));
    }
}
