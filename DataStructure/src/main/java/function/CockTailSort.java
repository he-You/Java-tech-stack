package function;

import java.util.Arrays;

/**
 * 鸡尾酒排序算法
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/7/11 23:33
 */
public class CockTailSort {
    /**
     * 鸡尾酒排序基础版
     *
     * @param array 待排序的数组
     */
    public static void sortBasic(int[] array) {
        int temp = 0;
        for (int i = 0; i < array.length / 2; i++) {
            // 有序标记，每轮的初始值都是 true
            boolean isSorted = true;
            // 奇数轮，从左向右排序比较和交换
            for (int j = i; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            // 偶数轮次开始之前将标志位置为 true;
            isSorted = true;
            // 偶数轮次，从右往左比较和交换
            for (int j = array.length - i - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 2, 4, 6, 3, 7, 1, 9, 8,0};
        sortBasic(array);
        System.out.println(Arrays.toString(array));
    }
}
