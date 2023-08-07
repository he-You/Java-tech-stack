package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-07 11:23
 */
public class Solution26 {
    public static int removeDuplicates(int[] nums) {
        int p = 0;
        int q = 1;
        while (q<nums.length) {
            if (nums[p]!=nums[q]) {
                nums[p+1] = nums[q];
                p++;
            }
            q++;
        }
        return p+1;
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{0,0,1,1,1,2,2,3,3,4}));
    }
}
