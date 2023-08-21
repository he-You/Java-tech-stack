package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-11 10:22
 */
public class Solution151 {
    public String reverseWords(String s) {
        // 分割成单词数组
        String[] strArray = s.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        // 将数组中的单词翻转
        reveseStr(strArray);
        for (int i = 0;i< strArray.length;i++) {
            // 忽略空字符
            if ((strArray[i].equals("")|| strArray[i].equals(" "))) {
                continue;
            }
            if (i > 0) {
                // 如果当前字符和前一个字符都是空格，跳过
                if ((strArray[i - 1].equals(strArray[i])) && strArray[i].equals(" ")) {
                    continue;
                }
                // 如果当前字符和前一个字符都不是空格，则添加一个空格
                if (!strArray[i-1].equals(" ") && !strArray[i].equals(" ")) {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append(strArray[i]);
        }
        return stringBuilder.toString();
    }


    public void reveseStr (String[] strArray) {
        int l = 0;
        int r = strArray.length - 1;
        while (l<r) {
            String tmp = strArray[l];
            strArray[l] = strArray[r];
            strArray[r] = tmp;
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        String s = "abcdefg";
        System.out.println(s.substring(0, 2));
        System.out.println(s.substring(2, s.length()));
        int[] next = getNext("aabaaf");
    }

    /**
     * 获取next数组
     *
     * @param s 主串
     * @param m 模式串
     * @return
     */
    public static int[] getNext (String m) {
        char[] mc = m.toCharArray();
        int[] next = new int[mc.length];
        // 初始化j
        // 定义两个指针i和j，j指向前缀末尾位置，i指向后缀末尾位置。
        int j = 0;
        next[0] = j;
        for (int i = 1; i< mc.length; i++) {
            // 处理前缀后缀不相同的情况：j向前回退
            while (j>0 && mc[i] != mc[j]) {
                j = next[j - 1];
            }
            // 前后缀长度一样时j向后移动
            if (mc[i] == mc[j]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
