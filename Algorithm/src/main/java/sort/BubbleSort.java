package sort;

/**
 * @author heyou
 * @date 2021-12-05 17:23
 */
public class BubbleSort {

    public static void bulleSort(int[] arr) {
        if (arr ==null || arr.length<2) {
            return;
        }
        for (int e = arr.length - 1; e > 0; e--) {
            boolean changeFlag = false;
            for (int i = 0; i < e; i++) {
                if (arr[i] > arr[i+1]) {
                    swap(arr, i, i+1);
                    changeFlag = true;
                }
            }
            if (!changeFlag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {3,5,1,4,6,2};
        bulleSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    /**
     * 0^n = n;n^n =0
     * a^b = b^a;
     * 异或满足交换律和结合律
     * 假设a=x;b=y
     * 则
     * a= x^y
     * b= x^y^y=a^0=x
     * a = x^y^x =0^y=y
     * @param arr
     * @param i
     * @param j
     */
    private static void swap(int[] arr, int i, int j){
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }
}
