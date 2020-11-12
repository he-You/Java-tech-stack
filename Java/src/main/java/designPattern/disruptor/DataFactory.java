package designPattern.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 产生 PCData 的工厂类，在初始化时构建所有的缓冲区中的对象实例
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/12 23:31
 */
public class DataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
