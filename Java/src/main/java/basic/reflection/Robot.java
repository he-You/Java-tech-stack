package basic.reflection;

/**
 * 简要说明:反射机制目标类
 *
 * @author heyou
 * @date 2019-12-23 19:07
 */
public class Robot {
    //属性
    private String name;
    //方法
    public void sayHi(String str){
        System.out.println(str+""+name);
    }
    private String sayHello(String tag){
        return "Hello"+" "+tag;
    }
}
