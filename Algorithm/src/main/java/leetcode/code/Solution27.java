package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-07 10:06
 */
public class Solution27 {
    public static int removeElement(int[] nums, int val) {
        // 双指针，快指针遍历数组，慢指针计算长度
        int slow = 0;
        for (int f = 0; f<nums.length;f++) {
            if (nums[f]!=val) {
                nums[slow] = nums[f];
                slow++;
            }
        }
        return slow;
    }

    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{0,1,2,2,3,0,4,2}, 2));
    }
}
