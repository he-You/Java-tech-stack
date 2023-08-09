package leetcode.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-09 15:18
 */
public class Solution15 {
    public static List<List<Integer>> threeSum(int[] nums) {
        // 对数组排序
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0;i < nums.length - 2; i++) {
            int left = i+1;
            int right = nums.length - 1;

            if (nums[i] > 0) {
                return res;
            }
            if (i>1 && nums[i] == nums[i-1]) {
                continue;
            }
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                // 判断三数之和
                if (sum > 0) {
                    right--;
                }
                if (sum < 0) {
                    left++;
                }
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (right > left && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    while (right > left && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 找到符合条件的结果后，左右指针向中间移动
                    right--;
                    left++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = threeSum(new int[]{-1,0,1,2,-1,-4});
        System.out.println();
    }
}
