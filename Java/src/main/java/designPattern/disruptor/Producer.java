package designPattern.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 生产者
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/12 23:34
 */
public class Producer {
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer byteBuffer){
        // 获取可用的序列号
        long sequence = ringBuffer.next();
        try {
            PCData event = ringBuffer.get(sequence);
            event.setValue(byteBuffer.getLong(0));
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
