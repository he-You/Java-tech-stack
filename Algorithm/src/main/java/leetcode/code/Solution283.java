package leetcode.code;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 *
 * @author heyou
 * @date 2023-08-06 11:40
 */
public class Solution283 {
    public static void moveZeroes(int[] nums) {
        if (nums.length == 1) {
            return;
        }
        // 遍历数组，统计0的个数,确定边界
        int index = 0;
        for (int i = 0;i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
        }
        for (int i = index; i < nums.length; i++) {
            nums[i]=0;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0};
        moveZeroes(nums);
        System.out.println("");
    }
}
