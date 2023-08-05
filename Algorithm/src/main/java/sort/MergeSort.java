package sort;

import java.util.Arrays;

/**
 * 归并排序
 * 把长度为n的输入序列分成两个长度为n/2的子序列；
 * 对这两个子序列分别采用归并排序；
 * 将两个排序好的子序列合并成一个最终的排序序列。
 *
 * @author heyou
 * @date 2023-07-29 15:37
 */
public class MergeSort {
    public static int[] mergeSort(int[] array) {
        int[] tmp = new int[array.length];
        spit(array,tmp, 0, tmp.length-1);
        return array;
    }

    // 分解
    private static void spit(int[] arr, int[] tmp, int left, int right) {
        if (left >= right) {
            return;
        }
        // 取start到end之间的中间位置mid
        int mid = (left + right) / 2;
        // 继续细分两个不同区间的数组
        spit(arr, tmp, left, mid);
        spit(arr, tmp, mid + 1, right);
        merge(arr, left, mid, right, tmp);

    }

    // 合并
    private static void merge(int[] pr, int[] pq, int[] qr) {

    }

    public static int[] merge(int[] left, int[] right) {
        // 申请额外的空间
        int[] result = new int[left.length + right.length];
        // index为新数组的原始下标，
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                result[index] = right[j++];
            } else {
                result[index] = left[i++];
            }
        }
        return result;
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        //左序列指针
        int i = left;
        //右序列指针
        int j = mid + 1;
        //临时数组指针
        int t = 0;
        while (i <= mid && j <= right) {
            // 判断两个区间中指针指向的值的大小，将较小的值放入到临时数组中
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        //将左边剩余元素填充进temp中
        while (i <= mid) {
            temp[t++] = arr[i++];
        }
        //将右序列剩余元素填充进temp中
        while (j <= right) {
            temp[t++] = arr[j++];
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 1, 4, 6, 2};
        int[] ints = mergeSort(arr);
        for (int i : ints) {
            System.out.println(i);
        }
    }
}
