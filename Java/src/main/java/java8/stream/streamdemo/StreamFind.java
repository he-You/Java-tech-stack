package java8.stream.streamdemo;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author heyou
 * @date 2020/2/27 21:36
 *  过滤流
 */
public class StreamFind {
    public static void main(String[] args) {
        Stream<Integer> integerStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        Optional<Integer> integerOptional = integerStream.filter(integer -> integer % 2 == 0).findAny();
        System.out.println(integerOptional.get());

        Stream<Integer> integerStream2 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        Optional<Integer> integerOptional2 = integerStream2.filter(integer -> integer % 2 == 0).findFirst();
        System.out.println(integerOptional2.get());


    }
}
