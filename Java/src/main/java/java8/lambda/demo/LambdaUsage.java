package java8.lambda.demo;

/**
 * @author heyou
 * @time 2020/2/5 11:55
 * @description Lambda的使用
 */
public class LambdaUsage {
    public static void main(String[] args) {
        Runnable r1 = () -> System.out.println("hello");
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        process(r1);
        process(r2);
        process(()->System.out.println("hello"));
    }

    private static void process(Runnable r){
        r.run();
    }
}
