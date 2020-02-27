package java8.stream.streamdemo;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author heyou
 * @date 2020/2/27 21:53
 */
public class StreamReduce {
    public static void main(String[] args) {
        Stream<Integer> integerStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        Integer result = integerStream.reduce(0, (x, y) -> x + y);

        System.out.println(result);

        integerStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        integerStream.reduce((i,j)->i+j).ifPresent(System.out::println);
    }
}
