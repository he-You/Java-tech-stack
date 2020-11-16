package designPattern.future;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/16 23:07
 */
public class RealData implements Data{
    protected  final  String result;
    public RealData(String para){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            stringBuffer.append(para);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = stringBuffer.toString();
    }

    @Override
    public String getResult() {
        return null;
    }
}
