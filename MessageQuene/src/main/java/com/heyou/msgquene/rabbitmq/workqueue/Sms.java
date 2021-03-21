package com.heyou.msgquene.rabbitmq.workqueue;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/21 下午10:49
 */
public class Sms {
    private String name;
    private String mobile;
    private String content;

    public Sms(String name, String mobile, String content) {
        this.name = name;
        this.mobile = mobile;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
