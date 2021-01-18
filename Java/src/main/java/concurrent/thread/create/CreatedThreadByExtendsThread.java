package concurrent.thread.create;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/1/10 下午2:37
 */
public class CreatedThreadByExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println(getName() + " is running");
    }

    public static void main(String[] args) {
        new CreatedThreadByExtendsThread().start();
        new CreatedThreadByExtendsThread().start();
        new CreatedThreadByExtendsThread().start();
        new CreatedThreadByExtendsThread().start();
    }
}
