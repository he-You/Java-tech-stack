package spi.impl;

import spi.SpiService;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/6/28 22:40
 */
public class SpiServiceFirstImpl implements SpiService {
    public SpiServiceFirstImpl() {
    }

    @Override
    public void printText(String text) {
        System.out.println("this is first implements:"+text);
    }

    @Override
    public void hello() {
        System.out.println("hello");
    }
}
