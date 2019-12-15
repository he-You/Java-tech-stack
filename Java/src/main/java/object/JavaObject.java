package object;

import org.openjdk.jol.info.ClassLayout;

/**
 * 简要说明:打印对象布局;一个对象的布局一般有以下几个部分组成
 * 1.对象头（64位虚拟机占12byte）
 * 2.实例数据
 * 3.对齐填充数据（根据实际情况，当对象头和实例数据在JVM中占的内存空间为8的倍数时，不需要进行填充）
 * 注：（64位）JVM只能识别以8的倍数的对象，所以会根据实际情况对Java对象进行数据的填充，确保该对象的大小为8的倍数
 * @author heyou
 * @date 2019-12-15 22:27
 */
public class JavaObject {
    private static JavaObject objectHead = new JavaObject();
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(objectHead).toPrintable());
    }
}
