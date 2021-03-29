package com.heyou.msgquene.rabbitmq.confirm;

import com.heyou.msgquene.rabbitmq.ConnectionUtils;
import com.heyou.msgquene.rabbitmq.RabbitConstant;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/28 下午4:57
 */
public class BaiduWeather {
    public static void main(String[] args) throws IOException {
        // 获取 TCP长连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取虚拟连接
        Channel channel = connection.createChannel();
        // 声明队列
        //第一个参数：队列名称ID
        //第二个参数：是否持久化，false对应不持久化数据，MQ停掉数据就会丢失
        //第三个参数：是否队列私有化，false则代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问
        //第四个：是否自动删除,false代表连接停掉后不自动删除掉这个队列
        //其他额外的参数, null
        channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null);
        //queueBind用于将队列与交换机绑定
        //参数1：队列名 参数2：交互机名  参数三：路由key
        channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "*.*.*.20201127");
        channel.basicQos(1);

        channel.basicConsume(RabbitConstant.QUEUE_BAIDU , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("百度天气收到气象信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
