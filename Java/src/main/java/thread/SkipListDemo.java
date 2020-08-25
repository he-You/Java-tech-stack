package thread;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 跳表
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/8/25 21:55
 */
public class SkipListDemo {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();

        for (int i = 0; i < 30; i++) {
            map.put(i,i);
        }
        // 跳表实现的 map元素是有序的
        for (Map.Entry<Integer, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
