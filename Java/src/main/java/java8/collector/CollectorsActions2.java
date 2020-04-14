package java8.collector;

import java8.stream.streamdemo.Dish;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/14 21:36
 */
public class CollectorsActions2 {
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
        testGroupingByConcurrentWithFunction();
        testGroupingByConcurrentWithFuctionAndCollector();
        testGroupingByConcurrentWithFuctionAndSupplierAndCollector();
        testJoining();
        testJoiningWithDelimiter();
        testJoiningWithDelimiterAndPreFixAndSuffix();
        testMapping();
        testMaxBy();
        testMinBy();
    }

    private static void testGroupingByConcurrentWithFunction(){
        System.out.println("testGroupingByConcurrentWithFunction");
        ConcurrentMap<Dish.Type, List<Dish>> concurrentMap = menu.stream().collect(Collectors.groupingByConcurrent(Dish::getType));
        Optional.ofNullable(concurrentMap.getClass()).ifPresent(System.out::println);
        Optional.ofNullable(concurrentMap).ifPresent(System.out::println);
    }

    private static void testGroupingByConcurrentWithFuctionAndCollector(){
        System.out.println("testGroupingByConcurrentWithFuctionAndCollector");
        ConcurrentMap<Dish.Type, Double> concurrentMap = menu.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType,Collectors.averagingDouble(Dish::getCalories)));

        Optional.ofNullable(concurrentMap).ifPresent(System.out::println);
    }

    // 跳表
    private static void testGroupingByConcurrentWithFuctionAndSupplierAndCollector(){
        System.out.println("testGroupingByConcurrentWithFuctionAndSupplierAndCollector");
        ConcurrentMap<Dish.Type, Double> concurrentMap = menu.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType, ConcurrentSkipListMap::new,Collectors.averagingDouble(Dish::getCalories)));
        Optional.of(concurrentMap.getClass()).ifPresent(System.out::println);
        Optional.ofNullable(concurrentMap).ifPresent(System.out::println);
    }

    private static void testJoining(){
        System.out.println("testJoining");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining())).ifPresent(System.out::println);
    }

    /**
     * 加入分隔符
     */
    private static void testJoiningWithDelimiter(){
        System.out.println("testJoiningWithDelimiter");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining(","))).ifPresent(System.out::println);
    }

    private static void testJoiningWithDelimiterAndPreFixAndSuffix(){
        System.out.println("testJoiningWithDelimiterAndPreFixAndSuffix");
        Optional.of(menu.stream().map(Dish::getName).collect(Collectors.joining(",","Names[","]"))).ifPresent(System.out::println);
    }

    private static void testMapping(){
        System.out.println("testMapping");
        Optional.of(menu.stream().collect(Collectors.mapping(Dish::getName,Collectors.joining(","))))
                .ifPresent(System.out::println);
    }

    private static void testMaxBy(){
        System.out.println("testMaxBy");
        menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))).ifPresent(System.out::println);
    }

    private static void testMinBy(){
        System.out.println("testMinBy");
        menu.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories))).ifPresent(System.out::println);
    }
}
