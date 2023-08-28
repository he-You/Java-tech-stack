package leetcode.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-28 23:27
 */
public class Solution93 {
    private List<String> result = new ArrayList<>();
    public List<String> restoreIpAddresses(String s) {
        if (s.equals("") || s.equals(" ")) {
            return result;
        }
        backTracking(new StringBuilder(s), 0, 0);
        return result;
    }

    public void backTracking (StringBuilder s, int startIndex, int pointNum) {
        if (pointNum == 3) {
            if (isValid(s, startIndex, s.length() - 1)) {
                result.add(s.toString());
            }
            return;
        }
        for (int i = startIndex; i< s.length (); i++) {
            if (isValid(s, startIndex, i)) {
                s.insert(i + 1, '.');
                backTracking(s, i + 2, pointNum+1);
                s.deleteCharAt(i + 1);
            } else {
                break;
            }
        }
    }
    private boolean isValid(StringBuilder s, int start, int end){
        if(start > end) {
            return false;
        }
        if(s.charAt(start) == '0' && start != end) {
            return false;
        }
        int num = 0;
        for(int i = start; i <= end; i++){
            int digit = s.charAt(i) - '0';
            num = num * 10 + digit;
            if(num > 255) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Solution93 solution93 = new Solution93();
        String str = "25525511135";
//        String substring1 = str.substring(0,1);
//        System.out.println(substring1);
//        String substring = str.substring(1);
//        System.out.println(substring);
        List<String> stringList = solution93.restoreIpAddresses(str);
        System.out.println("");
    }
}
