package java8.lambda.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

import static java.util.stream.Collectors.toList;

/**
 * @author heyou
 * @time 2020/2/5 12:03
 * @description Java8中常用的FunctionInterface的使用
 */
public class FunctionInterfaceUsage {
    private static List<Apple> filter(List<Apple> source, Predicate<Apple> predicate){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: source) {
            if(predicate.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    private static List<Apple> filterByWeight(List<Apple> source, LongPredicate predicate){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: source) {
            if(predicate.test(apple.getWeight())){
                result.add(apple);
            }
        }
        return result;
    }

    /**BiPredicate:入参有两个,对两个条件进行判断*/
    private static List<Apple> filterByBiPredicate(List<Apple> source, BiPredicate<String,Long> predicate){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: source) {
            if(predicate.test(apple.getColor(),apple.getWeight())){
                result.add(apple);
            }
        }
        return result;
    }

    private static void simpleTestConsumer(List<Apple> source, Consumer<Apple> consumer){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: source) {
            consumer.accept(apple);
        }
    }
    private static void simpleTestBiConsumer(String s,List<Apple> source, BiConsumer<Apple,String> biConsumer){
            List<Apple> result = new ArrayList<>();
            for (Apple apple: source) {
                biConsumer.accept(apple,s);
            }
        }

    private static String testFunction(Apple apple,Function<Apple,String> function){
        return function.apply(apple);
    }

    private static Apple testBiFunction(String color,long weight,BiFunction<String,Long,Apple> function){
        return function.apply(color,weight);
    }

    private static Apple createAppleBySupplier(Supplier<Apple> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(
                new Apple("green",120),
                new Apple("red",150)
        );

        List<Apple> greenList = filter(list,apple -> apple.getColor().equals("green"));
        List<Apple> weightList = filterByWeight(list,w -> w>100);
        List<Apple> biPredicateResult = filterByBiPredicate(list,(s,w)-> s.equals("green") && w > 100);
        List<Apple> resultList =  list.stream().filter(apple -> apple.getWeight()>130).collect(toList());


        System.out.println(greenList);
        System.out.println(weightList);
        System.out.println(biPredicateResult);
        System.out.println(resultList);
        System.out.println("==============================");
        simpleTestConsumer(list,apple -> System.out.println(apple));
        System.out.println("==============================");
        simpleTestBiConsumer("xxxxx",list,(apple,s)-> System.out.println(apple.getColor()+s));

        String stringResult = testFunction(new Apple("yellow",100),apple -> apple.toString());
        System.out.println(stringResult);

        IntFunction<Double> f = i->i*100.0d;
        double doubleResultf = f.apply(10);
        System.out.println(doubleResultf);

        Apple newApple = testBiFunction("blue",130,(color,weight)->new Apple(color,weight));
        System.out.println(newApple);
        Supplier<String> s = String::new;
        System.out.println(s.get().getClass());
        Apple a = createAppleBySupplier(()->new Apple("green",100));
        System.out.println(a);
    }
}
