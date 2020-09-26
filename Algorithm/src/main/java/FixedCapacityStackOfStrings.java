/**
 * 定容字符串栈的抽象数据类型与实现
 *
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2020/9/26 22:20
 */
public class FixedCapacityStackOfStrings<Item> {
    private Item[] a;

    private int N;

    public FixedCapacityStackOfStrings(int cap){
        a = (Item[]) new Object[cap];
    }
    public boolean isEmpty(){
        return N==0;
    }
    public int size(){
        return N;
    }

    public void push(Item item){
        a[N+1] = item;
    }

    public Item pop(){
        return a[--N];
    }

    public static void fnPrint(String exp){
        String[] split = exp.split(" ");
        FixedCapacityStackOfStrings f = new FixedCapacityStackOfStrings(20);
        for (String s:split) {
            // 这里要多判断一下,f函数的成员变量n是否为零.如果为零,则说明数组里面没东西了,不能用pop()去取数据,否则则会报错
            if(s.equals("-") && !f.isEmpty()){
                // 取数据的前提是:数组里面还有数据才可以
                Object pop = f.pop();
                System.out.println(pop);
            }else{
                f.push(s);
            }
        }
        System.out.println("-----------------end-------------------");
        System.out.println(f.size()+" left on stack ");

    }

    public static void main(String[] args) {
        String exp="to be or not to - be - - that - - - is";
        fnPrint(exp);
    }
}
