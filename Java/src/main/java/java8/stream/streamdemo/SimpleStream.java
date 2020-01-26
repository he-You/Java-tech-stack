package java8.stream.streamdemo;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @author heyou
 * @time 2020/1/26 21:31
 */
public class SimpleStream {
    /**
     * 对Dish进行筛选排序,留下卡路里<400的Dish的名称
     * @param args
     */
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork",false,800),
                new Dish("beef",false,700),
                new Dish("chicken",false,400),
                new Dish("french fries",true,530),
                new Dish("rice",false,350),
                new Dish("season fruit",true,120),
                new Dish("pizza",true,550),
                new Dish("prawns",false,300),
                new Dish("salmon",false,450)
        );
        List<String> dishNameByCollections = getDishNameByCollections(menu);
        List<String> dishNameByStream = getDishNameByStream(menu);
        System.out.println(dishNameByCollections);
        System.out.println(dishNameByStream);
    }
    /**传统方法:通过Collections容器进行操作*/
    private static List<String> getDishNameByCollections(List<Dish> menu){
        List<Dish> lowCalories = new ArrayList<>();
        for (Dish d:menu) {
            if(d.getCalories()<400){
                lowCalories.add(d);
            }
        }
        //排序
        Collections.sort(lowCalories,(d1,d2)->Integer.compare(d1.getCalories(),d2.getCalories()));

        List<String> dishNameList =  new ArrayList<>();
        for (Dish d: lowCalories) {
            dishNameList.add(d.getName());
        }
        return dishNameList;
    }
    /**Stream方式处理*/
    private static List<String> getDishNameByStream(List<Dish> menu){
        return  menu.stream().filter(dish -> dish.getCalories()<400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }
}
