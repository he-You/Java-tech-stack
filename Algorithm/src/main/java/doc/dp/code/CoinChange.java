package doc.dp.code;

/**
 * leetcode322
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/9/29 23:36
 */
public class CoinChange {
    public static int coinChange(int[] coins, int amount) {
        if (amount <= 0) {
            return 0;
        }
        // 记录最小兑换次数
        int[] f = new int[amount + 1];
        //填充1个硬币能兑换到的金额
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount) {
                f[coins[i]] = 1;
            }
        }
        // f[x] = min{f[x-coins[i]]+1};
        for (int i = 0; i < f.length; i++) {
            //当前金额无法兑换到，所以不能进行下一步
            if (f[i] == 0) {
                continue;
            }
            for (int j = 0; j < coins.length; j++) {
                //提交后发现测试用例里有个int边界值，需要排除
                if ((long) i + (long) coins[j] > Integer.MAX_VALUE) {
                    continue;
                }
                //当前金额 加上一个硬币的金额 必须小于等于 目标金额amount
                if (i + coins[j] <= amount) {
                    if (f[i + coins[j]] == 0 || f[i] + 1 < f[i + coins[j]]) {
                        f[i + coins[j]] = f[i] + 1;
                    }
                }
            }
        }
        return f[amount] == 0 ? -1 : f[amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[]{2,5,7};
        int amount = 27;
        System.out.println(coinChange(coins,amount));
    }
}
