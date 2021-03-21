package com.heyou.msgquene.rabbitmq.workqueue;

import com.heyou.msgquene.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/21 下午10:56
 */
public class SmsSender1 {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("rabbitmq_order", false, false, false, null);

        // 如果不写basicQos（1），则自动MQ会将所有请求平均发送给所有消费者
        // basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），在从队列中获取一个新的
        channel.basicQos(1);

        channel.basicConsume("rabbitmq_order",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                System.out.println("SMSSender1-短信发送成功:" + jsonSMS);

                try {
                    // 模拟不同的业务处理时长不一样
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
