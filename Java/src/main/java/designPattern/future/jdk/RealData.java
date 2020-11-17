package designPattern.future.jdk;

import java.util.concurrent.Callable;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/17 22:53
 */
public class RealData implements Callable<String> {
    private String para;
    public RealData(String para){
        this.para = para;
    }
    @Override
    public String call() throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(para);
            Thread.sleep(100);
        }
        return stringBuffer.toString();
    }
}
