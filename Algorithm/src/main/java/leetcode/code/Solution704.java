package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-06 22:32
 */
public class Solution704 {
    public static int search(int[] nums, int target) {
        // 找出数组中间位置
        // 判断target在数组中的哪一部分
        // 判断数组的首尾是否等于target
        // 如果不等于继续折半
        return getMid(nums, 0, nums.length-1, target);
    }

    public static int getMid (int[] nums, int l, int r, int target) {
        if (nums[l] == target) {
            return l;
        }
        if (nums[r] == target) {
            return r;
        }
        if (r-l<2) {
            return -1;
        }
        int mid = l+(r-l)/2;
        if (nums[mid] > target) {
            // 在左半边
            return getMid(nums, l, mid, target);
        }
        if (nums[mid] < target) {
            // 在右半边
            return getMid(nums, mid+1, r, target);
        }
        return mid;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {-1,0,3,5,9,12};
        System.out.println(search(nums, 9));
    }
}
