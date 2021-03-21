package com.heyou.msgquene.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/21 下午10:34
 */
public class ConnectionUtils {
    public static Connection getConnection(){
        // 定义连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        //5672是RabbitMQ的默认端口号
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("heyou");
        connectionFactory.setPassword("heyou");
        connectionFactory.setVirtualHost("/rabbit_mq_0321");
        // 获取TCP长连接
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
