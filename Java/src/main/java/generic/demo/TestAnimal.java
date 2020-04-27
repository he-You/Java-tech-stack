package generic.demo;

import java.util.ArrayList;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/4/27 22:19
 */
public class TestAnimal {
    public static void main(String[] args) {
        ArrayList<Animal> animals = new ArrayList<>();
        ArrayList<Cat> cats = new ArrayList<>();
        ArrayList<MiniCat> miniCats = new ArrayList<>();

       // showAnimal2(miniCats); error
    }

    /**
     * 泛型上限通配符传递的集合类型只能说Cat类型或Cat的子类类型
     * @param list
     */
    public static void showAnimal(ArrayList<? extends Cat> list){
        //list.add(new Cat()); error:因为采用上限通配符，程序无法获知要传入何种类型的数据
        for (int i = 0; i < list.size(); i++) {
            Cat cat = list.get(i);
            System.out.println(cat);
        }
    }

    /**
     * 泛型下限通配符传递的集合类型只能说Cat类型或Cat的父类类型
     * @param list
     */
    public static void showAnimal2(ArrayList<? super Cat> list){
        //list.add(new Cat()); error:因为采用上限通配符，程序无法获知要传入何种类型的数据
        for (Object obj:list) {
            System.out.println(obj);
        }
    }
}
