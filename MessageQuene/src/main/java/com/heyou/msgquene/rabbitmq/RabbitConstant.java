package com.heyou.msgquene.rabbitmq;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/28 下午4:55
 */
public interface RabbitConstant {
    String EXCHANGE_WEATHER = "exchange_weather";

    String EXCHANGE_WEATHER_ROUTING = "exchange_weather_routing";
    String EXCHANGE_WEATHER_TOPIC = "exchange_weather_topic";
    String QUEUE_SINA = "queue_sina";
    String QUEUE_BAIDU = "queue_baidu";
}
