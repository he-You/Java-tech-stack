package doc.dp.code;

/**
 * leetcode322
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/9/29 23:36
 */
public class CoinChange {
    public static int coinChange(int[] coins, int amount) {
        // 合法性盘
        if (coins.length < 1) {
            return -1;
        }
        if (amount < 1) {
            return 0;
        }
        // 获取硬币的个数
        int n = coins.length;
        // 初始化记忆数组
        int[] f = new int[amount + 1];
        f[0] = 0;
        // 状态转移方程 f[x] = f[x-i]+1
        for (int i = 1; i < amount; i++) {
            // 整个状态下的最优解
            int min = Integer.MAX_VALUE;
            // 遍历所有可能的状态
            for (int j = 0; j < n; j++) {
                // 当前的硬币可以组成amount则进行更新
                if (i >= coins[j] && f[i - coins[j]] < min) {
                    min = f[i - coins[j]] + 1;
                }
            }
            // 保存结果
            f[i] = min;
        }
        return f[amount] == Integer.MAX_VALUE ? -1 : f[amount];

    }

    public static void main(String[] args) {
        int[] coins = new int[]{2, 5, 7};
        int amount = 27;
        System.out.println(coinChange(coins, amount));
    }
}
