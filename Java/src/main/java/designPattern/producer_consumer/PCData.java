package designPattern.producer_consumer;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/10 23:53
 */
public class PCData {
    private final int intData;
    public PCData(int d){
        intData = d;
    }

    public PCData(String d){
        intData = Integer.valueOf(d);
    }

    public int getData(){
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}
