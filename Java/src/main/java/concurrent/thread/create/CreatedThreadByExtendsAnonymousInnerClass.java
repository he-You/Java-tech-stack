package concurrent.thread.create;

/**
 * 通过继承 Thread匿名内部类创建线程
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午2:41
 */
public class CreatedThreadByExtendsAnonymousInnerClass {
    public static void main(String[] args) {
        // 初始化线程实例
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " is running");
            }
        }.start();
    }
}
