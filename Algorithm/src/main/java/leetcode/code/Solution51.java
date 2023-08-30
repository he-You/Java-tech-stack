package leetcode.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heyou
 * @date 2023-08-29 23:06
 */
public class Solution51 {
    List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
        char[][] chessboard = new char[n][n];
        for (char[] c : chessboard) {
            Arrays.fill(c, '.');
        }
        backtracking(n, 0, chessboard);
        return res;
    }

    /**
     * 递归回溯
     *
     * @param n nxn的棋盘
     * @param row 当前行数
     * @param chessboard 棋盘
     */
    public void backtracking (int n, int row, char[][] chessboard) {
        if (row == n) {
            res.add(addList(chessboard));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (isValid(row, col, n, chessboard)) {
                chessboard[row][col] = 'Q';
                backtracking(n, row+1, chessboard);
                chessboard[row][col] = '.';
            }
        }
    }

    public List<String> addList (char[][] chessboard){
        List<String> list = new ArrayList<>();
        for (char[] c : chessboard) {
            list.add(String.copyValueOf(c));
        }
        return list;
    }

    public boolean isValid (int row, int col, int n, char[][] chessboard) {
        // 检查列
        for (int i=0; i<row; i++) {
            if (chessboard[i][col] == 'Q') {
                return false;
            }
        }
        // 检查对角线
        // 检查45度对角线，当row
        for (int i = row-1, j = col-1; i>=0 && j>=0; i--, j--) {
            if (chessboard[i][j] == 'Q') {
                return false;
            }
        }
        // 检查135度对角线
        for (int i=row-1, j=col+1; i>=0 && j<=n-1; i--, j++) {
            if (chessboard[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution51 solution51 = new Solution51();
        List<List<String>> lists = solution51.solveNQueens(4);
        System.out.println("");
    }
}
