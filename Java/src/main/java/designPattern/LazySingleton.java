package designPattern;

/**
 * 单例模式-懒汉模式
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/11/9 23:17
 */
public class LazySingleton {
    private LazySingleton(){
        System.out.println("Singleton is created");
    }
    private static LazySingleton instance = new LazySingleton();

    public static synchronized LazySingleton getInstance(){
        // 当 instacne 第一次被调用时创建单例对象，防止对象被多次创建
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
