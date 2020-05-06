package collections.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/5/6 21:22
 */
public class ListApi {


    /**
     *     交集	listA.retainAll(listB)	listA内容变为listA和listB都存在的对象	listB不变
     *     差集	listA.removeAll(listB)	listA中存在的listB的内容去重	        listB不变
     *     并集	listA.removeAll(listB)/listA.addAll(listB)	为了去重，listA先取差集，然后追加全部的listB	    listB不变
      * @param args
     */
    public static void main(String[] args) {
        // 交集
        List<String> listA01 = new ArrayList<String>() {{
            add("A");
            add("B");
        }};
        List<String> listB_01 = new ArrayList<String>() {{
            add("B");
            add("C");
        }};
        listA01.retainAll(listB_01);
        // 结果:[B]
        System.out.println(listA01);
        // 结果:[B, C]
        System.out.println(listB_01);

        // 差集
        List<String> listA_02 = new ArrayList<String>() {{
            add("A");
            add("B");
        }};
        List<String> listB_02 = new ArrayList<String>() {{
            add("B");
            add("C");
        }};
        listA_02.removeAll(listB_02);
        // 结果:[A]
        System.out.println(listA_02);
        // 结果:[B, C]
        System.out.println(listB_02);

        // 并集
        List<String> listA_03 = new ArrayList<String>() {{
            add("A");
            add("B");
        }};
        List<String> listB_03 = new ArrayList<String>() {{
            add("B");
            add("C");
        }};
        listA_03.removeAll(listB_03);
        listA_03.addAll(listB_03);
        // 结果:[A, B, C]
        System.out.println(listA_03);
        // 结果:[B, C]
        System.out.println(listB_03);
    }
}
