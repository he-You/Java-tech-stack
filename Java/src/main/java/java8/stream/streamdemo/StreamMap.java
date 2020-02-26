package java8.stream.streamdemo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author heyou
 * @date 2020/2/26 22:31
 */
public class StreamMap {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,7,7,3,3,4,4,5,1,8);

        List<Integer> result = list.stream().map(i -> i * 2).collect(toList());
        System.out.println(result);

        listDish().stream().map(dish -> dish.getName()).forEach(System.out::println);
        //flatMap(扁平化):对Stream中的对象再次进行stream操作
        String[] words ={"Hello","world"};

        Stream<String[]> stream = Arrays.stream(words).map(word -> word.split(""));
        Stream<String> stringStream = stream.flatMap(Arrays::stream);
        stringStream.distinct().forEach(System.out::println);
    }

    private static List<Dish> listDish(){
        List<Dish> menu = Arrays.asList(
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
        return menu;
    }
}
