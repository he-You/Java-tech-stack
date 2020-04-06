package java8.collector;

import java8.stream.streamdemo.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/6 22:01
 */
public class CollectorsActions {
    private final static List<Dish> menu = Arrays.asList(
            new Dish("pork",false,800,Dish.Type.MEAT),
            new Dish("beef",false,700,Dish.Type.MEAT),
            new Dish("chicken",false,400,Dish.Type.MEAT),
            new Dish("french fries",true,530,Dish.Type.OTHER),
            new Dish("rice",false,350,Dish.Type.OTHER),
            new Dish("season fruit",true,120,Dish.Type.OTHER),
            new Dish("pizza",true,550,Dish.Type.OTHER),
            new Dish("prawns",false,300,Dish.Type.FISH),
            new Dish("salmon",false,450,Dish.Type.FISH)
    );

    public static void main(String[] args) {
        testAveragingDouble();
        testAveragingInt();
        testAveragingLong();
        testCollectingAndThen();
    }

    private static void testAveragingDouble(){
        System.out.println("testAveragingDouble");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories)))
        .ifPresent(System.out::println);
    }

    private static void testAveragingInt(){
        System.out.println("testAveragingInt");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(Dish::getCalories)))
        .ifPresent(System.out::println);
    }

    private static void testAveragingLong(){
        System.out.println("testAveragingLong");
        Optional.ofNullable(menu.stream().collect(Collectors.averagingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    private static void testCollectingAndThen(){
        System.out.println("testCollectingAndThen");
        Optional.ofNullable(menu.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.averagingInt(Dish::getCalories),a -> "The result is "+a)))
                .ifPresent(System.out::println);
    }
}
