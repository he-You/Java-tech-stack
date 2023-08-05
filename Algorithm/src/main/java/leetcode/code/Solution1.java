package leetcode.code;

import java.util.HashMap;

/**
 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 *
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author heyou
 * @date 2023-07-31 22:31
 */
public class Solution1 {
    public static int[] twoSum(int[] nums, int target) {
        int[] index = new int[2];
        HashMap<Integer, Integer> numIndexMap = new HashMap<>(nums.length);
        for (int i = 0; i<nums.length; i++) {
            if (numIndexMap.containsKey(nums[i])) {
                index[0] = i;
                index[1] = numIndexMap.get(nums[i]);
            }
            numIndexMap.put(target-nums[i], i);
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {2,7,11,15,3};
        twoSum(arr, 9);
    }
}
