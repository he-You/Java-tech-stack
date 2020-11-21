package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 奇偶排序的并行实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/21 22:25
 */
public class ParallelOddEvenSort {
    static int[] arr= {25,48,65,87,123,233,456,666,777,8999,55555};

    static ExecutorService pool = Executors.newCachedThreadPool();

    static int exchFlag = 1;	//记录当前迭代是否发生了数据交换

    public static synchronized int getExchFlag() {
        return exchFlag;
    }

    public static synchronized void setExchFlag(int v) {
        exchFlag = v;
    }

    public static void pOddEvenSort(int[] arr) throws InterruptedException {
        int start = 0;			//记录交换类型。0为偶交换，1为奇交换

        //如果上次比较发生了数据交换，或者当前正在进行奇交换，循环不会停止；
        //直到程序不再发生交换，或者当前进行的是偶交换，即奇偶交换已经成对出现。
        while(getExchFlag() == 1 || start ==1) {
            setExchFlag(0);
            //偶数的数值长度，当start=1时，只有len/2-1个线程。倒计时线程数。
            CountDownLatch latch = new CountDownLatch(arr.length/2 -(arr.length%2 == 0 ? start :0));
            for(int i =start; i < arr.length-1; i+=2) {
                pool.submit(new OddEvenSortTask(i,latch));
            }
            latch.await();//等待所有线程结束
            start = start ==0 ? 1 : 0;
        }
    }

    static class OddEvenSortTask implements Runnable{
        int i;
        CountDownLatch latch;

        public OddEvenSortTask(int i, CountDownLatch latch) {
            super();
            this.i = i;
            this.latch = latch;
        }

        @Override
        public void run() {
            if(arr[i] > arr[i+1]) {
                int temp = arr[i];
                arr[i]   = arr[i+1];
                arr[i+1] = temp;
                setExchFlag(1); //数据进行了交换
            }
            latch.countDown();//结束当前线程的任务，倒计时器-1
        }
    }

    public static void main(String[] args) throws InterruptedException {
        pOddEvenSort(arr);
        for (int i : arr) {
            System.out.print(i+" ");
        }
    }
}
