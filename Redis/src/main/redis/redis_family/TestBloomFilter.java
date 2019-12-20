package redis_family;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 简要说明:布隆过滤器的简单实现
 *
 * @author heyou
 * @date 2019-12-20 18:26
 */
public class TestBloomFilter {
    //100w个数据
    private static int total = 1000000;
    private static BloomFilter bloomFilter = BloomFilter.create(Funnels.integerFunnel(), total);

    public static void main(String[] args) {
        // 初始化100w条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bloomFilter.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("元素检查未命中");
            }
        }

        // 匹配不在过滤器中的50000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 50000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误判的数量：" + count);
    }
}
