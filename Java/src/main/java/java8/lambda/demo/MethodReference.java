package java8.lambda.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author heyou
 * @time 2020/2/6 13:00
 * @description Lambda方法推导
 *              i:可以使用类的静态方法进行推导
 *              ii:对象的实例方法
 *              iii:通过构造方法
 */
public class MethodReference {
    public static void main(String[] args) {
        Consumer<String> consumer = (s -> System.out.println(s));
        useConsumer(consumer,"Hello World");
        //方法推导方式
        useConsumer(System.out::println,"hello world");

        List<Apple> list = Arrays.asList(
                new Apple("green",110),
                new Apple("Red",120),
                new Apple("aab",125)
        );
        System.out.println(list);
        list.sort((a1,a2)->a1.getColor().compareTo(a2.getColor()));
        list.sort(Comparator.comparing(Apple::getColor));
        System.out.println(list);
        list.forEach(System.out::println);

        int value = Integer.parseInt("123");
        //类的静态方法进行推导
        Function<String,Integer> f =Integer::parseInt;
        Integer result = f.apply("123");
        System.out.println(result);

        BiFunction<String,Integer,Character> f2 = String::charAt;
        Character c = f2.apply("hello",2);
        System.out.println(c);

        String string = new String("Hello");
        //对象的实例方法进行推导
        Function<Integer,Character> f3= string::charAt;
        Character c2 = f3.apply(4);
        System.out.println(c2);
        //构造方法推导
        BiFunction<String,Long,Apple> appleFunction = Apple::new;
        Apple apple = appleFunction.apply("red",150L);
        System.out.println(apple);
        //自定义Function针对多个入参的构造方法
        ThreeFunction<String,Integer,Long,ComplexApple> threeFunction = ComplexApple::new;
        ComplexApple complexApple = threeFunction.apply("red",156,23L);
        System.out.println(complexApple);
    }

    private static <T> void useConsumer(Consumer<T> consumer,T t){
        consumer.accept(t);
        consumer.accept(t);
    }
}
