package concurrent.thread;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 无锁的对象引用：对普通对象的引用进行封装，保证修改对象引用的线程安全性
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/9/24 22:06
 */
public class AtomicReferenceDemo {
    private static AtomicReference<Integer> money =new AtomicReference<>(19);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        while (true){
                            Integer m = money.get();
                            if (m<20) {
                                if (money.compareAndSet(m,m+20)){
                                    System.out.println("余额小于 20，充值成功，余额："+money.get()+"元");
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }.start();
        }
    }
}
