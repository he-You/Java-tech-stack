package basic.reflection;

import java.lang.reflect.Field;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/6/22 23:51
 */
public class Test {
    public static void main(String[] args) throws Exception{
        User e = new User();
        reflect(e);
    }

    public static void reflect(User e) throws Exception{
        Class cls = e.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
            System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(e));
        }
    }
}
