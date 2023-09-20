package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-30 15:07
 */
public class Solution121 {
    public int maxProfit(int[] prices) {
        int start = 0;
        int end = 0;
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < prices[start]) {
                start = i;
                end = start +1;
            }
            end = end > prices.length -1 ? prices.length - 1 : end;
            if (prices[i] >= prices[end]) {
                end = i;
            }
            if (end > start) {
                profit = Math.max(prices[end] - prices[start], profit);
            }
        }
        return profit;
    }

    public static void main(String[] args) {
        Solution121 solution121 = new Solution121();
        solution121.maxProfit(new int[] {7,6,5,3,2});
    }
}
