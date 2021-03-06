package concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author heyou(heyou_0423 @ 163.com)
 * @date 2021/3/6 下午4:18
 */
public class AtomicRefrenceDemo {
    public static void main(String[] args) {
        // 声明原子更新的引用类型
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User user = new User("zhangsan",25);
        atomicReference.set(user);
        User updatePerson = new User("lisi", 20);
        // 原子更新引用类型中的属性，调用 unsafe 的compareAndSwapObject更新对象方法
        atomicReference.compareAndSet(user, updatePerson);

        System.out.println(atomicReference.get().getUserName());
        System.out.println(atomicReference.get().getAge());
    }

    static class User{
        private String userName;

        private int age;

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
