package leetcode.code;

/**
 * @author heyou
 * @date 2023-08-03 00:01
 */
public class Solution822 {
    public static int flipgame(int[] fronts, int[] backs) {
        for (int i = 0; i < fronts.length; i++) {
            if (fronts[i] != backs[i]) {
                // 正反面不一样，尝试比较，正反面一样的不做考虑
                int target = fronts[i];
                int tmp = backs[i];
                // 反转：交换值
                fronts[i] = tmp;
                backs[i] = target;
                // 判断backs[i]是否在 fronts中
                boolean isExist = false;
                for (int item : fronts) {
                    if (item == backs[i]) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    return backs[i];
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,1};
        int[] b = new int[]{1,2};

//        int[] a = new int[]{1,2,4,4,7};
//        int[] b = new int[]{1,3,4,1,3};
        System.out.println(flipgame(a, b));
    }
}
