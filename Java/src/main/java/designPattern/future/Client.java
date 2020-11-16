package designPattern.future;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/16 23:13
 */
public class Client {
    public Data request(final String queryStr){
        final FutureData future = new FutureData();
        new Thread(){
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                future.setRealData(realData);
            }
        }.start();
        return future;
    }
}
