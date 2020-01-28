### 函数式编程

所谓函数式编程就是将函数（一段操作）作为一个基本单位进行传递。以前的Java中参数只能是具体的变量，函数式编程打破这一规范，可以将整个方法作为一个参数传递。

Java毕竟是面向对象的编程语言，你要传递的东西，必须是一个类或接口的对象或者一个基本类型变量，所以Java就定义了函数式接口，用来承载传递的函数。

### 函数式接口

函数式接口是在JDK1.8中提出的新概念，但对应的却是老结构，在以往版本的JDK中就已经存在这种结构，只是没有定义化。

函数式接口就是只有一个抽象方法的接口。常用的函数式接口有Runnable、Comparator等。

JDK1.8将这些接口取了一个统一的名称函数式接口，为了规范化，同时避免用户自定义函数式接口时错误的添加了其他的抽象方法，
而定义了一个注解：@FunctionalInterface，凡是由该注解标注的接口，统统为函数式接口，强制性的只有一个抽象方法。

为了函数式接口的扩展，JDK对接口规范进行了进一步修改，接口中除了可以定义抽象方法之外，还能够定义静态方法，和默认方法，
而且这两种方法可以拥有自己的实现。其中静态方法一般作为工具方法，而默认方法是可以被继承重写的，
还能拥有一个默认的实现。除此之外，函数式接口中还可以重写Object中定义的方法。
```java
// 典型函数式接口
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}

// 自定义函数式接口
@FunctionalInterface
public interface Itest {
    void test();
    // 重写Object中的方法
    boolean equals(Object obj);
    default void defaultMethod(){
        // 这是一个默认方法
    }
    static void staticMethod(){
        // 这是一个静态方法
    }
}
```

### Lambda表达式

Lambda表达式，简化了匿名内部类的操作方式。

Lamnda表达式可以用在两个地方，一种是集合遍历，另一种就是替换匿名内部类。

前者基于Iterable接口和Map接口中定义的forEach方法，后者则依据函数式接口。

- forEach方法
其实forEach方法是对函数式接口的有效利用，将遍历的书写流程简化，我们不用再写一大堆的for循环框架代码。
```
public class LanbdaTest {
    public static void main(String[] args) {
        List<String> list = Collections.EMPTY_LIST;
        list.forEach(System.out::println);
        Map<String,Object> map = Collections.EMPTY_MAP;
        map.forEach((k,v) -> System.out.println(k + ":"+ v));
    }
}
```
forEach方法的参数是Consumer或者BiConsumer，主要用于消费资源，即需要提供参数，
但是没有返回值的方法（函数或操作）。

forEach方法最开始是在Iterable接口和Map接口中定义的，这是以默认方法的方式定义的，
分别以Consumer和BiConsumer作为入参。

Iterable中的forEach方法：
```
public interface Iterable<T> {
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
}
```
Iterable的实现类均可以通过重写该方法来自定义遍历的方式。

比如以数组为底层结构的ArrayList、CopyOnWriteArrayList、CopyOnWriteArraySet等都是
以普通for循环来实现的遍历。而以链表为底层结构的LinkedList则没有重写forEach方法，
采用默认方法中简化的for循环，编译器会对其进行处理，将其采用Iterator进行遍历。

- Map中的forEach方法：
```
public interface Map<K,V> {
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    } 
}
```
在常用的HashMap和TreeMap中都对该方法进行了重写，HashMap采用数组+链表（红黑树）的方式实现，
但是遍历的时候采用的就是数组+链表双重遍历，因为在HashMap中的红黑树同时还是一个双向链表。
而TreeMap中则是使用树结构的**中序遍历**方式实现的。

#### 替换匿名内部类

Lambda替换匿名内部类有一个前提，那就是这个匿名内部类的接口类型必须为函数式接口，
如果不是函数式接口，是无法使用Lambda替换的。

常用的函数式接口为Runnable，使用匿名内部类方式如下：
```
public class LambdaTest {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("采用匿名内部类");
            }
        });
    }
}
```
使用Lambda替换如下：
```
public class LambdaTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->System.out.println("采用Lambda方式"));
    }
}
```
Lambda表达式最大的作用其实就是替换匿名内部类，简化这种写法。

### 方法引用
方法引用出现的目的是为了解决所需的操作已经存在的情况。

当我们需要传递的操作已经存在，那就不必再费尽心思的再写一个出来了，直接使用方法引用来将已有的方法给它就行了。

方法引用使用“::”双英文冒号组成的操作符来指定方法。

这种形式参数不再是显式传递，采用方法引用之后，参数会自动传递，我们举个例子看看简单的原理：
```java
public class LanbdaTest {
    public static String getName(Supplier<String> supplier){
        return supplier.get();
    }
    public static void main(String[] args) {
        Person person = new Person("huahua");
        System.out.println(getName(person::getName));
    }
}
class Person{
    private String name;
    public Person(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
```
执行结果为：
```
huahua
```

解析：
首先我们使用了方法引用：person::getName，Person类中有已定义好的获取name的方法，这里就可以直接引用该方法。
Supplier是供应者，可以无中生有，也就是不需要参数，产生一个返回值。

Person中的getName方法，明显就符合Supplier的格式，没有参数，但是返回了一个结果，
所以这里就可以直接传递person::getName。

方法引用的种类：

- 类的构造器引用：ArrayList::new、String[]::new
- 类的静态方法引用：String::valueOf、Integer::valueOf
- 类的实例方法引用：String::length、Person::getName
- 对象的实例方法引用：sring::length、person::getName
方法引用于Lambda可以算是平等，并列的关系，Lambda用于自定义操作，方法引用用于引用已存在的操作。





