package java8.stream.streamdemo;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author heyou
 * @date 2020/2/29 23:21
 * 已知数据类型,可以选择对应类型的Stream进行处理,可节省空间
 */
public class NumericStream {
    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1,2,3,4,5,6,7});
        Integer result = stream.filter(integer -> integer > 3).reduce(0, Integer::sum);

        stream = Arrays.stream(new Integer[]{1,2,3,4,5,6,7});
        //ObjectStream转IntStream
        int sum = stream.mapToInt(Integer::intValue).filter(i -> i > 3).sum();
        System.out.println(sum);

        int a = 9;
        IntStream.rangeClosed(1,100).filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                //IntStream转成ObjectStream
                .boxed()
                .map(b -> new int[]{a,b,(int)Math.sqrt(a*a + b*b)})
                .forEach(r->System.out.println("a="+r[0]+" b="+r[1]+" c="+r[2]));

        IntStream.rangeClosed(1,100).filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                //IntStream转成ObjectStream
                .mapToObj(b -> new int[]{a,b,(int)Math.sqrt(a*a + b*b)})
                .forEach(r->System.out.println("a="+r[0]+" b="+r[1]+" c="+r[2]));
    }
}
