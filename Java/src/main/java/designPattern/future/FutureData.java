package designPattern.future;

/**
 * FutureData 是 Future的真实数据的代理，封装 RealData 的等待过程
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/16 23:06
 */
public class FutureData implements Data{
    protected RealData realData = null;
    protected boolean isReady = false;
    public synchronized  void setRealData(RealData realData) {
        if (isReady){
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }
    public synchronized String getReasult(){
        while(!isReady){
            try{
                wait();
            } catch (InterruptedException e){

            }
        }
        return realData.getResult();
    }
    @Override
    public String getResult() {
        return null;
    }
}
