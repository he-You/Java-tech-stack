package generic;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/19 21:47
 */
public class Generic<T> {
    /**
     * 泛型可变参数的定义
     * @param e
     * @param <E>
     */
    @SafeVarargs
    public static <E> void print(E... e){
        for (E value : e) {
            System.out.println(value);
        }
    }
}
