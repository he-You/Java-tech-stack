package concurrent;

/**
 * 插入排序
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/23 23:03
 */
public class InsertSort {
    public static void insertSort(int[] arr) {
        int length = arr.length;
        int j, i, key;
        for (i = 1; i < length; i++) {
            // key为要准备插入的元素
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            // 找到合适的位置插入 key
            arr[j + 1] = key;
        }
    }
}
