package leetcode.code;

/**
 * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
 *
 * @author heyou
 * @date 2023-08-07 16:47
 */
public class Solution59 {
    public int[][] generateMatrix(int n) {
        // 返回数据
        int[][] res = new int[n][n];
        // 每次循环的开始点(start, start)
        int start = 0;
        // 循环次数
        int loop = 0;
        // 循环过程中的坐标（i,j）
        int i,j;
        int count = 1;
        while (n/2>loop++) {
            // 处理[0,n-offset)区间的边
            // 模拟上侧从左到右
            for (j = start; j < n - loop; j++) {
                res[start][j] = count++;
            }
            // 模拟右侧从上到下
            for (i = start; i < n - loop; i++) {
                res[i][j] = count++;
            }
            // 模拟下侧从右到左
            for (; j >= loop; j--) {
                res[i][j] = count++;
            }
            // 模拟左侧从下到上
            for (; i >= loop; i--) {
                res[i][j] = count++;
            }
            start++;
        }
        if (n % 2 == 1) {
            res[start][start] = count;
        }
        return res;
    }
}
