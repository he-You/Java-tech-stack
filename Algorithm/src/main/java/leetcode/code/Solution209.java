package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-07 15:01
 */
public class Solution209 {
    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int result = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                result = Math.min(result, right - left + 1);
                sum -= nums[left++];
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }

    public static int sum(int[] nums, int s, int e) {
        int total = 0;
        for (int i = s; i<=e; i++) {
            total = total + nums[i];
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
    }
}
