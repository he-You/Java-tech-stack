package com.heyou.msgquene.rabbitmq.confirm;

import com.heyou.msgquene.rabbitmq.ConnectionUtils;
import com.heyou.msgquene.rabbitmq.RabbitConstant;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/28 下午4:54
 */
public class WheatherCenter {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        // 开启监听模式
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                //第二个参数代表接收的数据是否为批量接收，一般我们用不到。
                System.out.println("消息已被Broker接收,Tag:" + l );
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                System.out.println("消息已被Broker拒收,Tag: " + l);
            }
        });
        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return r) {
                System.err.println("===========================");
                System.err.println("Return编码：" + r.getReplyCode() + "-Return描述:" + r.getReplyText());
                System.err.println("交换机:" + r.getExchange() + "-路由key:" + r.getRoutingKey() );
                System.err.println("Return主题：" + new String(r.getBody()));
                System.err.println("===========================");
            }
        });
        Map<String, String> data = getWeatherData();
        for (Map.Entry<String, String> itemData : data.entrySet()) {
            //第一个参数交换机名字   其他参数和之前的一样
            // 第二个参数表示消息的routingkey
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC, itemData.getKey(), null, itemData.getValue().getBytes());
        }
//        channel.close();
//        connection.close();
    }

    public static Map<String, String> getWeatherData() {
        Map<String, String> area = new LinkedHashMap<>();
        area.put("china.hunan.changsha.20201127", "中国湖南长沙20201127天气数据");
        area.put("china.hubei.wuhan.20201127", "中国湖北武汉20201127天气数据");
        area.put("china.hunan.zhuzhou.20201127", "中国湖南株洲20201127天气数据");
        area.put("us.cal.lsj.20201127", "美国加州洛杉矶20201127天气数据");

        area.put("china.hebei.shijiazhuang.20201128", "中国河北石家庄20201128天气数据");
        area.put("china.hubei.wuhan.20201128", "中国湖北武汉20201128天气数据");
        area.put("china.henan.zhengzhou.20201128", "中国河南郑州20201128天气数据");
        area.put("us.cal.lsj.20201128", "美国加州洛杉矶20201128天气数据");
        return area;
    }
}
