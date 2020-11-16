package designPattern.future;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/16 23:15
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据="+data.getResult());
    }
}
