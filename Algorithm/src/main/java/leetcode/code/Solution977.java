package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-07 14:11
 */
public class Solution977 {
    public static int[] sortedSquares(int[] nums) {
        // 双指针
        int l =0;
        int r = nums.length-1;
        int[] res = new int[nums.length];
        int index = res.length - 1;
        while (l <= r) {
            if (cal(nums[l])< cal(nums[r])) {
                // 右边的大，将右边的指针向前移动一位
                res[index] = cal(nums[r]);
                index--;
                r--;
            } else {
                res[index] = cal(nums[l]);
                index--;
                l++;
            }
        }
        return res;
    }

    public static int cal(int num) {
        return num*num;
    }

    public static void main(String[] args) {
        System.out.println(sortedSquares(new int[]{-5,-3,-2,-1,4}));
    }
}
