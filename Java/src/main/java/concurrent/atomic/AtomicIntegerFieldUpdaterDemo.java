package concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/6 下午5:38
 */
public class AtomicIntegerFieldUpdaterDemo {
    // 声明原子更新的引用类型
    private static AtomicIntegerFieldUpdater<User> atomicReference = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");

    public static void main(String[] args) {
        User user = new User("zhangsan",20);
        System.out.println(atomicReference.getAndIncrement(user));
        System.out.println(atomicReference.get(user));
    }

    static class User{
        private String userName;

        public volatile int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
