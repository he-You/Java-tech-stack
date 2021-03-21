package com.heyou.msgquene.rabbitmq.workqueue;

import com.google.gson.Gson;
import com.heyou.msgquene.rabbitmq.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/21 下午10:48
 */
public class OrderSystem {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("rabbitmq_order", false, false, false, null);

        for(int i = 1 ; i <= 100 ; i++) {
            Sms sms = new Sms("乘客" + i, "13900000" + i, "您的车票已预订成功");
            String jsonSMS = new Gson().toJson(sms);
            channel.basicPublish("" , "rabbitmq_order" , null , jsonSMS.getBytes());
        }
        System.out.println("发送数据成功");
        channel.close();
        connection.close();
    }
}
