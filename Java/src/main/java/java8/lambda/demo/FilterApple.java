package java8.lambda.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heyou
 * @time 2020/1/29 10:52
 * @description 根据条件筛选对应的Apple;使用lambda表达式可以节省代码量以及内存空间
 */
public class FilterApple {
    /**如果接口中只有一个抽象方法,是可以标注成'@FunctionalInterface',代表该接口可以使用lambda表达式进行操作的*/
    @FunctionalInterface
    public interface AppleFilter{
        boolean filter(Apple apple);
    }
    /**当需要过滤的苹果属性不确定时,本方法可以根据需求进行筛选满足属性的苹果*/
    public static List<Apple> findApple(List<Apple> apples,AppleFilter appleFilter){
        List<Apple> list = new ArrayList<>();
        for(Apple apple:apples){
            if(appleFilter.filter(apple)){
                list.add(apple);
            }
        }
        return list;
    }

    public static class GreenAnd160WeightFilter implements AppleFilter{

        @Override
        public boolean filter(Apple apple) {
            return apple.getColor().equals("green")&&apple.getWeight()>=160;
        }
    }

    public static class RedLess150WeightFilter implements AppleFilter{

        @Override
        public boolean filter(Apple apple) {
            return apple.getColor().equals("red")&&apple.getWeight()<=150;
        }
    }

    /**筛选指定的颜色*/
    public static List<Apple> findGreenApple(List<Apple> apples){
        List<Apple> list = new ArrayList<>();
        for (Apple apple:apples) {
            if("green".equals(apple.getColor())){
                list.add(apple);
            }
        }
        return  list;
    }
    /**当需要过滤的苹果颜色不确定时,本方法可以根据需求进行筛选对应的颜色*/
    public static List<Apple> findApple(List<Apple> apples,String color){
        List<Apple> list = new ArrayList<>();
        for (Apple apple:apples) {
            if(color.equals(apple.getColor())){
                list.add(apple);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        //苹果的库存
        List<Apple> appleList = Arrays.asList(
                new Apple("green",150),
                new Apple("red",120),
                new Apple("green",170)
        );
        List<Apple> greenApples = findGreenApple(appleList);
        System.out.println(greenApples);
        List<Apple> apples = findApple(appleList,"red");
        System.out.println(apples);

        List<Apple> result = findApple(appleList,new GreenAnd160WeightFilter());
        System.out.println(result);

        /*通过内部类方式*/
        List<Apple> redResult = findApple(appleList, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redResult);

        /*通过lambda表达式*/
        List<Apple> lambdaResult = findApple(appleList,(Apple apple) -> {
            return apple.getColor().equals("green");
        });
        //上述lambda可以简化成
        List<Apple> lambdaResult2 = findApple(appleList,apple -> {
            return apple.getColor().equals("green");
        });
        System.out.println(lambdaResult);

    }
}
