package java8.stream.streamdemo;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author heyou
 * @date 2020/2/27 21:19
 * 匹配流
 */
public class StreamMatch {
    public static void main(String[] args) {
        Stream<Integer> integerStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //满足所有成立
        boolean match = integerStream.allMatch(integer -> integer > 0);
        System.out.println(match);
        //流操作一次执行(中断)完,需要重新创建一个流
        Stream<Integer> integerStream2 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //只需要满足一个就成立
        boolean anyMatch = integerStream2.anyMatch(integer -> integer > 6);
        System.out.println(anyMatch);
        Stream<Integer> integerStream3 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        boolean noneMatch = integerStream3.noneMatch(integer -> integer < 0);
        System.out.println(noneMatch);
    }
}
