package java8.stream.streamdemo;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author heyou
 * @date 2020/2/26 22:18
 */
public class StreamFilter {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,7,7,3,3,4,4,5,1,8);
        //获取偶数
        List<Integer> result = list.stream().filter(i -> i % 2 == 0).collect(toList());
        System.out.println(result);
        //去重
        List<Integer> distinctResult = list.stream().distinct().collect(toList());
        System.out.println(distinctResult);
        //跳过前n个元素
        List<Integer> skipResult = list.stream().skip(5).collect(toList());
        System.out.println(skipResult);
        //限制长度
        List<Integer> limitResult = list.stream().limit(5).collect(toList());
        System.out.println(limitResult);
    }
}
