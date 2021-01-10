package concurrent.thread;

import java.util.Arrays;
import java.util.List;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午3:14
 */
public class CreatedThreadByParallelStream {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 串行，打印结果为12345
        list.stream().forEach(System.out::print);
        System.out.println();
        // 并行，打印结果随机，比如35214
        list.parallelStream().forEach(System.out::print);
    }
}
