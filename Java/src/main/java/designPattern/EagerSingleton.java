package designPattern;

/**
 * 单例模式-饿汉模式
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/9 23:16
 */
public class EagerSingleton {
    private EagerSingleton(){
        System.out.println("Singleton is created");
    }

    /**
     * 1.instance 必须为 private 并且是 static，这两个条件是保证 instance的安全性
     * 2.getInstance()必须是static
     */
    private static EagerSingleton instance = new EagerSingleton();

    public static EagerSingleton getInstance() {
        return instance;
    }
}
