package leetcode.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 *
 * @author heyou
 * @date 2023-07-31 22:55
 */
public class Solution49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 遍历字符串数组
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        for (String str : strs) {
            // 转char数组
            char[] chars = str.toCharArray();
            // 对char数组排序
            Arrays.sort(chars);
            // 将排序后的重新拼接
            String key = String.valueOf(chars);
            // 尝试放入hashmap中
            if (hashMap.containsKey(key)) {
                // 如果存在，则说明是字母异位词
                List<String> stringList = hashMap.get(key);
                stringList.add(str);
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                hashMap.put(key, arrayList);
            }
        }
        return new ArrayList<>(hashMap.values());
    }
}
