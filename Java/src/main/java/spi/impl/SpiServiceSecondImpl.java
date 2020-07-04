package spi.impl;

import spi.SpiService;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/6/28 22:45
 */
public class SpiServiceSecondImpl implements SpiService {
    public SpiServiceSecondImpl() {
    }

    @Override
    public void printText(String text) {
        System.out.println("this is second implements:"+text);
    }
    @Override
    public void hello() {
        System.out.println("hello");
    }

}
