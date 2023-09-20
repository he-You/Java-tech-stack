package doc.dp.code;

/**
 * @author heyou
 * @date 2023-08-31 16:06
 */
public class BagProblem {

    public static void main(String[] args) {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagSize = 4;
        testWeightBagProblem2(weight, value, bagSize);
    }

    /**
     * 动态规划获得结果
     *
     * @param weight  物品的重量
     * @param value   物品的价值
     * @param bagSize 背包的容量
     */
    public static void testWeightBagProblem(int[] weight, int[] value, int bagSize) {
        // 物品数量
        int goods = weight.length;
        // 创建dp数组

        int[][] dp = new int[goods][bagSize + 1];

        // 初始化dp数组
        /**
         * dp[i][j]的含义是：dp[i][j] 表示从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少。
         * dp[i][j]是最大价值是：
         *  1. 当放入第i个物品，总重量超过背包容量时，最大价值为 dp[i-1][j]（即前i-1个物品在容量为j的背包中的最大价值）
         *  2. 当放入第i个物品，总重量未超过背包容量时，最大价值为 dp[i-1][j-weight[i]]+value[i] （即前i-1个物品，使用了j-weight[i]的重量时的价值+第i个物品的价值）
         *  而物品只有放入和不放入两两种状态，综上可知：dp[i][j]为上述两种情况中的最大值
         */
        // dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight[i]] + value[j])
        // 初始化
        for (int i = 0; i < goods; i++) {
            dp[i][0] = 0;
        }
        for (int j = weight[0]; j <= bagSize; j++) {
            dp[0][j] = value[0];
        }
        // 填充dp数组
        // 遍历物品
        for (int i = 1; i < goods; i++) {
            // 遍历背包的尺寸
            for (int j = 0; j <= bagSize; j++) {
                // 如果背包的尺寸小于物品i的重量，则最大价值去
                if (j < weight[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }

        // 打印dp数组
        System.out.println("");
    }

    /**
     * 动态规划获得结果
     *
     * @param weight  物品的重量
     * @param value   物品的价值
     * @param bagSize 背包的容量
     */
    public static void testWeightBagProblem2(int[] weight, int[] value, int bagSize) {
        // 用一维数组维护最大价值
        int[] dp = new int[bagSize + 1];
        // 状态转移方程：dp[j] = Math.max(dp[j], dp[j-weight[i]]+value[i])
        dp[0] = 0;
        for (int i = 0; i < weight.length; i++) {
            for (int j = bagSize; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        System.out.println("");
    }
}
