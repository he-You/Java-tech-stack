package function;

/**
 * 冒泡排序
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/7/11 22:07
 */
public class BubbleSort {
    /**
     * 原始版本的冒泡排序
     *
     * @param array 待排序的数组
     */
    public static void sortV1(int[] array) {
        // 外部循环控制轮次
        for (int i = 0; i < array.length - 1; i++) {
            // 内部循环实现冒泡处理
            for (int j = 0; j < array.length - i - 1; j++) {
                int temp;
                if (array[j] > array[j + 1]) {
                    // 交换逻辑
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序的改进：使用标志位，如果标志位为 true，则说明本轮排序的元素有序，无需进行交换
     *
     * @param array 待排序的数组
     */
    public static void sortV2(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            // 有序标记，每一轮的初始值都是 true;
            boolean isSorted = true;
            for (int j = 0; j < array.length - i - 1; j++) {
                int temp = 0;
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    // 有元素交换，即表示不是有序的，标记为 false
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    /**
     * 冒泡排序性能升级版：在每轮排序后记录下最后一次元素交换的位置，该位置即为无序数列的边界sortBorder，往后就是有序的了
     *
     * @param array 待排序的数组
     */
    public static void sortV3(int[] array) {
        // 记录最后一次交换的位置
        int lastExchangeIndex = 0;
        // 无序数列的边界，每次比较只需要比较到这里
        int sortBorder = array.length - 1;
        for (int i = 0; i < array.length - 1; i++) {
            // 有序标记，每轮的初始值为 true
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                int temp = 0;
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    // 有元素交换，即表示不是有序的，标记为 false
                    isSorted = false;
                    // 更新最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (isSorted) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{5, 2, 4, 6, 3, 7, 1, 9, 8, 0};
        sortV1(array);
    }
}
