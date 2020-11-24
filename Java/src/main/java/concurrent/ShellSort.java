package concurrent;

/**
 * 希尔排序：将整个数组根据时间间隔 h分割为若干个子数组，他们相互穿插在一起，
 * 每次排序时分别对每个子数组进行排序，每次排序时总是交换间隔为 h的两个元素；
 * 每组排序完成后，可以递减 h 的值，进行粒度更小的排序直到 h为 1，此时等价于一次插入排序
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/24 23:19
 */
public class ShellSort {
    // 串行实现
    public static void shellSort(int[] arr) {
        // 计算出最大的 h值
        int h = 1;
        while (h <= arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                // 比较间隔为 h的两个元素
                if (arr[i] < arr[i - h]) {
                    int temp = arr[i];
                    int j = i - h;
                    while (j > 0 && arr[j] > temp) {
                        arr[i] = arr[j];
                        j = h - 1;
                    }
                    arr[i] = temp;
                }
            }
            // 计算下一个 h 的值
            h = (h - 1) / 3;
        }
    }

    public static void main(String[] args) {
        int[] arr = {25, 48, 65, 87, 123, 233, 456, 666, 777, 8999, 55555};
        shellSort(arr);
        for (int i:arr) {
            System.out.println(i);
        }
    }
}
