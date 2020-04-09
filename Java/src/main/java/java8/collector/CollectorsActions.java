package java8.collector;

import java8.stream.streamdemo.Dish;

import java.util.*;
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
        testCounting();
        testGroupingByFunction();
        testGroupingByFunctionAndCollector();
        testGroupingByFunctionAndSupplierAndCollector();
        testSummarizingInt();
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
        List<Dish> dishList = menu.stream().filter(dish -> dish.getType().equals(Dish.Type.MEAT))
                // Collections::unmodifiableList -使流处理完后的list不可修改
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        // dishList.add(new Dish("",false,100,Dish.Type.OTHER));
        System.out.println(dishList);
    }

    private static void testCounting(){
        System.out.println("testCounting");
        Optional.of(menu.stream().collect(Collectors.counting())).ifPresent(System.out::println);
    }
    // 分组
    private static void testGroupingByFunction(){
        System.out.println("testGroupingByFunction");
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType))).ifPresent(System.out::println);
    }

    private static void testGroupingByFunctionAndCollector(){
        System.out.println("testGroupingByFunctionAndCollector");
        // 返回每种类型的的数量
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType,Collectors.counting())))
                .ifPresent(System.out::println);
    }

    private static void testGroupingByFunctionAndSupplierAndCollector(){
        System.out.println("testGroupingByFunctionAndSupplierAndCollector");
        // 包装成指定的map
        Map<Dish.Type, Double> map = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,TreeMap::new, Collectors.averagingInt(Dish::getCalories)));
        Optional.of(map.getClass()).ifPresent(System.out::println);
        Optional.of(map).ifPresent(System.out::println);
    }

    private static void testSummarizingInt(){
        System.out.println("testSummarizingInt");
        // 统计各项指标的数据值
        IntSummaryStatistics summaryStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        Optional.of(summaryStatistics).ifPresent(System.out::println);
    }
}
