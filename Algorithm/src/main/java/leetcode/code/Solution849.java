package leetcode.code;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heyou
 * @date 2023-08-22 23:42
 */
public class Solution849 {
    public static int maxDistToClosest(int[] seats) {
        Map<Integer, int[]> distIndexMap = new HashMap<>();
        // 最长连续0的
        int start = -1;
        int end = -1;
        int maxKey = -1;
        for (int i = 0; i < seats.length; i++) {
            if (start == -1 && seats[i] == 0) {
                start = i;
            }
            if (start != -1 && seats[i] == 0) {
                end = i;
            }
            if (start != -1 && (seats[i] == 1 || i == seats.length-1)) {
                int dis = end-start+1;
                if (dis > maxKey) {
                    distIndexMap.clear();
                    distIndexMap.put(dis, new int[]{start, end});
                    maxKey = dis;
                }
                start = -1;
                end = -1;
            }
        }
        //
        if (distIndexMap.isEmpty()) {
            return 0;
        }
        int[] ints = distIndexMap.get(maxKey);
        int startIndex = ints[0];
        int endIndex = ints[ints.length-1];
        if (startIndex == 0) {
            return 0;
        }
        if (endIndex == seats.length - 1) {
            return seats.length - 1;
        }
        return (ints[ints.length-1]+1+ints[0])/2;
    }

    public static void main(String[] args) {
        System.out.println(maxDistToClosest(new int[]{1, 0, 0, 0}));
    }
}
