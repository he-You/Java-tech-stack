package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-06 14:41
 */
public class Solution11 {
    public static int maxArea(int[] height) {
        // 面积 s= min*(maxIndex-minIndex)
        int left = 0;
        int right = height.length-1;
        int res = 0;
        while (left<right) {
            int area = Math.min(height[left], height[right])*(right-left);
            res = Math.max(res, area);
            if (height[left]< height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,8,6,2,5,4,8,3,7};
        int res = maxArea(arr);
        System.out.println(res);
    }
}
