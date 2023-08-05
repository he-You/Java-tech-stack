package leetcode.code;

/**
 * 给你一个下标从 0 开始的整数数组nums，它表示英雄的能力值。如果我们选出一部分英雄，这组英雄的 力量定义为：
 *
 * i0，i1，... ik表示这组英雄在数组中的下标。那么这组英雄的力量为max(nums[i0],nums[i1] ... nums[ik])的平方 * min(nums[i0],nums[i1] ... nums[ik]) 。
 * 请你返回所有可能的 非空 英雄组的 力量 之和。由于答案可能非常大，请你将结果对109 + 7取余。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/power-of-heroes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author heyou
 * @date 2023-08-01 22:45
 */
public class Solution2681 {
    public int sumOfPower(int[] nums) {
        // 获取数组的所有子数组组合
        int start = 0;
        int end = 0;
        int total = 0;
        for (int i = start; i< nums.length; i++) {

        }
        return 0;
    }

    private int childArraySum (int[] childNums) {
        int max = childNums[0];
        int min = childNums[0];
        for (int i = 0; i<childNums.length; i++) {
            if (childNums[i]> max) {
                max = childNums[i];
            }
            if (childNums[i] < min) {
                min = childNums[i];
            }
        }
//        sum = (max*max + min)/(Math.pow(10,9)+7)
        return max*max*min;
    }

    public static void main(String[] args) {
        System.out.println(10/3);
    }
}
