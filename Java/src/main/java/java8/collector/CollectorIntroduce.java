package java8.collector;

import java8.lambda.demo.Apple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/5 23:27
 */
public class CollectorIntroduce {
    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(
                new Apple("green",130),
                new Apple("green",190),
                new Apple("green",140),
                new Apple("green",110),
                new Apple("red",120),
                new Apple("yellow",125)
        );
        // 聚合
        List<Apple> greenList = list.stream().filter(a -> a.getColor().equals("green")).collect(Collectors.toList());
        Optional.ofNullable(greenList).ifPresent(System.out::println);
        // 分组
        Optional.ofNullable(groupByNormal(list)).ifPresent(System.out::println);

        Optional.ofNullable(groupByFunction(list)).ifPresent(System.out::println);

        Optional.ofNullable(groupByCollector(list)).ifPresent(System.out::println);
    }

    private static Map<String,List<Apple>> groupByNormal(List<Apple> apples){
        Map<String,List<Apple>> map = new HashMap<>();
        for(Apple a:apples){
            List<Apple> list = map.get(a.getColor());
            if(null == list){
                list = new ArrayList<>();
                map.put(a.getColor(),list);
            }
            list.add(a);
        }
        return map;
    }

    private static Map<String,List<Apple>> groupByFunction(List<Apple> apples){
        Map<String,List<Apple>> map = new HashMap<>();
        apples.stream().forEach(apple -> {
            List<Apple> colorList = Optional.ofNullable(map.get(apple.getColor())).orElseGet(()->{
                List<Apple> list = new ArrayList<>();
                map.put(apple.getColor(),list);
                return list;
            });
            colorList.add(apple);
        });
        return map;
    }

    private static Map<String,List<Apple>> groupByCollector(List<Apple> apples){
        return apples.stream().collect(Collectors.groupingBy(Apple::getColor));
    }
}
