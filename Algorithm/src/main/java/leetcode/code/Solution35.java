package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-06 23:44
 */
public class Solution35 {
    public static int searchInsert(int[] nums, int target) {
        if (target< nums[0]) {
            return 0;
        }
        if (target > nums[nums.length-1]) {
            return nums.length;
        }
        return getMid(nums, 0, nums.length-1, target);
    }

    public static int getMid (int[] nums, int l, int r, int target) {
        if (target == nums[l]) {
            return l;
        }
        if (target == nums[r]) {
            return r;
        }
        if (r - l < 2 && nums[l]< target && nums[r]> target) {
            return l+1;
        }
        int mid = l+(r-l)/2;
        if (nums[mid] > target) {
            return getMid(nums, l, mid-1, target);
        }
        if (nums[mid] < target) {
            return getMid(nums, mid+1, r, target);
        }
        return mid;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,5};
        int res = searchInsert(arr, 3);
        System.out.println(res);
    }
}
