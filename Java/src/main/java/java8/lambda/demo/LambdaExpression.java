package java8.lambda.demo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author heyou
 * @time 2020/2/4 10:31
 * @description lambda表达式基础
 *  1.lambda表达式的语法格式:("参数列表") ->"表达式体";其中,参数列表不需要入参的类型,返回体不需要return,如果要写return,则需要在表达式体前后用{}包裹,
 *  在lambda表达式中return和{}必须同时出现
 *  2.语法:
 *      i:(parameters)->expression
 *      ii:(parameters)->{statments;}
 *      iii:()->{}
 *      iV:()->"Hello"
 *      v:()->{return ""}
 */
public class LambdaExpression {
    public static void main(String[] args) {
        /*传统方式*/
        Comparator<Apple> byColor = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
            }
        };
        List<Apple> list = Collections.emptyList();
        list.sort(byColor);

        /*lambda*/
        Comparator<Apple> byColor2 = ((o1, o2) -> o1.getColor().compareTo(o2.getColor()));
        System.out.println(byColor2);

        /*valid lambda*/
        /*Consumer:接收一个参数,不返回值*/
        Consumer<String> stringConsumer = (String s)->s.length();
        /*Function:接收一个参数,返回一个参数*/
        Function<String, Integer> functionLambda = s -> s.length();
        /*Predicate:返回布尔值*/
        Predicate<Apple> p = a -> a.getColor().equals("green");

        Runnable r = ()->{};

    }
}
