package com.heyou.msgquene.rabbitmq.pubsub;

import com.heyou.msgquene.rabbitmq.ConnectionUtils;
import com.heyou.msgquene.rabbitmq.RabbitConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/28 下午4:54
 */
public class WheatherCenter {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //第一个参数交换机名字   其他参数和之前的一样
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER,"" , null , "hello weather".getBytes());
        channel.close();
        connection.close();
    }
}
