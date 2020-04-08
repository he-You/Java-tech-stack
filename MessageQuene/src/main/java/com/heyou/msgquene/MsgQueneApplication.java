package com.heyou.msgquene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/8 21:52
 */
@SpringBootApplication
public class MsgQueneApplication {
    /**
     * 默认启动方式
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MsgQueneApplication.class,args);
    }
    /**
     * 不依赖内建的Tomcat启动,使用外部Tomcat服务器
     */
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(MsgQueneApplication.class);
    }
}
