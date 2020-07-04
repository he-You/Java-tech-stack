package spi.impl;

import spi.SpiService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/6/28 22:49
 */
public class SpiMain {
    public static void main(String[] args) {
        ServiceLoader<SpiService> loader = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> it = loader.iterator();
        for(SpiService service : loader){
            service.hello();
            service.printText("hello spi");
        }
    }
}
