package designPattern.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * 消费者
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/12 23:29
 */
public class Consumer implements WorkHandler<PCData> {
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event:--" + pcData.getValue() * pcData.getValue() + "--");
    }
}
