package concurrent;

/**
 * 插入排序：将未排序的数组（或链表）拆成两部分，前半部分是已排序的，后半部分是未排序的。
 * 在进行排序的时候，只需要在未排序的部分选择一个元素，将其插入前面的有序的数组中即可。初始时假设已排序的部分就是第一个元素。
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
