package generic.demo;

/**
 * 类型通配符测试
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/27 21:57
 */
public class Test {
    public static void main(String[] args) {
        Box<Number> box = new Box<>();
        box.setFirst(2);
        showBox(box);

        Box<Integer> box2 = new Box<>();
        box.setFirst(3);
        showBox(box2);
    }

    public static void showBox(Box<? extends Number> box){
        Number first = box.getFirst();
        System.out.println(first);
    }
}
