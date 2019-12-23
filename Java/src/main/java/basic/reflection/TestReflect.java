package basic.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 简要说明:测试反射机制
 *
 * @author heyou
 * @date 2019-12-23 19:11
 */
public class TestReflect {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        Class tagetClass =  Class.forName("basic.reflection.Robot");
        //创建类的对象实例
        Robot robot = (Robot) tagetClass.newInstance();
        System.out.println("class name is :"+tagetClass.getName());
        //getDeclaredMethod获取所有的方法,不能获取继承的方法以及实现接口的方法
        Method method = tagetClass.getDeclaredMethod("sayHello",String.class );
        //设置私有方法可获取
        method.setAccessible(true);
        Object object = method.invoke(robot,"heyou2");
        System.out.println("result: "+object);

        //getMethod只能获取该类public方法,也可以获取继承的方法和实现接口的方法
        Method method1 = tagetClass.getMethod("sayHi", String.class);
        method1.invoke(robot,"Welcome");

        //获取私有属性
        Field field = tagetClass.getDeclaredField("name");
        field.setAccessible(true);
        field.set(robot,"HeYou");
        method1.invoke(robot,"Welcome");


    }
}
