package leetcode.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-26 22:19
 */
public class Solution228 {
    public static List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }
        if (nums.length == 1) {
            res.add(String.valueOf(nums[0]));
            return res;
        }
        int start = 0;
        for(int i = 0; i + 1 < nums.length; i++) {
            if (nums[i+1] - nums[i] != 1) {
                String area;
                if (start == i) {
                    area = String.valueOf(nums[start]);
                } else {
                    area = nums[start] + "->" + nums[i];
                }
                res.add(area);
                start = i+1;
            }
        }
        if (start  < nums.length) {
            res.add(nums[start] + "->" + nums[nums.length-1]);
        }
        return res;
    }

    public static void main(String[] args) {
        List<String> stringList = summaryRanges(new int[]{0,2,3,4,6,8,9});
        System.out.println("");
    }
}
