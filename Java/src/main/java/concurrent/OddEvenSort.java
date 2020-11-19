package concurrent;

/**
 * 奇偶交换排序：奇交换和偶交换
 * 奇交换：总是比较奇数索引及其相邻的后续元素
 * 偶交换：总是比较偶数索引及其相邻的后续元素
 * 奇交换与偶交换成对出现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/19 23:17
 */
public class OddEvenSort {
    // 串行实现
    public static void oddEvenSort(int[] arr){
        int exchFlag = 1;
        int start = 0;
        while (exchFlag == 1 || start == 1){
            exchFlag = 0;
            for (int i = start; i < arr.length-1; i+=2) {
                if (arr[i]>arr[i+1]){
                    int temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    exchFlag = 1;
                }
            }
            if (start == 0){
                start =1;
            } else {
                start = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5,52,6,3,4};
        oddEvenSort(arr);
    }
}
