package sort;

/**
 * 初始已排序区间只有 一个元素，就是数组的第一个元素。
 * 插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，
 * 并保证已排序区间数据一直有序。重复这个过程，直到未排序区间中元素为空，算法结束。
 *
 * @author heyou
 * @date 2023-07-26 06:00
 */
public class InsertionSort {
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 数组的第一个元素默认为有序的
        for (int i = 1; i < arr.length; i++) {
            // 缓存当前值
            int value = arr[i];
            int j = i-1;
            // 查找插入位置，对位置j之前的元素进行检查
            for (; j >= 0; j--) {
                // 判断arr[j]是否大于a[i],即判断i前面的元素是否大于i
                if (arr[j] > value) {
                    // 将a[j]数据向后移动
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            // 插入数据
            arr[j+1] = value;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {3,5,1,4,6,2};
        insertionSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
