package jvm.loader;

/**
 * 简要说明:
 *
 * @author heyou
 * @date 2019-12-24 19:57
 */
public class ClassLoaderCheck {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader m = new MyClassLoader("","");
        Class c = m.loadClass("");
        System.out.println(c.getClassLoader());
        c.newInstance();
    }
}
