### Java引用类型总结



Java存在4种引用，有强到弱依次是：强引用、软引用、弱引用、虚引用。



- 强引用（Strong Reference）：通常我们通过new来创建一个新对象时返回的引用就是一个强引用，若一个对象通过一系列强引用可到达，它就是强可达的(strongly reachable)，那么它就不被回收。
- 软引用（Soft Reference）：软引用和弱引用的区别在于，若一个对象是弱引用可达，无论当前内存是否充足它都会被回收，而软引用可达的对象**在内存不充足时才会被回收**，因此软引用要比弱引用“强”一些。
- 弱引用（Weak Reference）：弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了**只具有弱引用**的对象，不管当前内存空间足够与否，都会回收它的内存。
- 虚引用（Phantom Reference）：虚引用是Java中最弱的引用，那么它弱到什么程度呢？它是如此脆弱以至于我们通过虚引用甚至无法获取到被引用的对象，虚引用存在的唯一作用就是当它指向的对象被回收后，虚引用本身会被加入到引用队列中，用作记录它指向的对象已被回收。



判断弱引用对象的关键在于只具有弱引用的对象，也就是说，如果一个对象有强引用，那么在系统GC时，是不会回收此对象的，也不会释放弱引用。

#### 为什么使用弱引用

Java常通过使用弱引用来**避免内存泄漏**，例如在JDK中有一种内存变量ThreadLocal，通过ThreadLocal变量可以使共享的变量在不同的线程中有不同的副本，原理是在每一个Thread有一个threadLocalMap的属性，用来存放ThreadLocal对象，ThreadLocalMap中是通过一个Entry[]的散列表存放ThreadLocal变量以及ThreadLocal的value，而作为Entry的key的ThreadLocal就是使用的弱引用

```java
static class Entry extends WeakReference<ThreadLocal<?>> {
    /** The value associated with this ThreadLocal. */
    Object value;

    Entry(ThreadLocal<?> k, Object v) {
        super(k);
        value = v;
    }
}
```



Entry通过继承了WeakReference并通过get、set设置ThreadLocal为Entry的referent。

**这里为什么要使用弱引用呢？**

原因是如果不使用弱引用，那么当持有value的强引用释放掉后，当线程没有回收释放时，threadLocalMap会一直持有ThreadLocal以及value的强应用，导致value不能够被回收，从而造成内存泄漏。

通过使用弱引用，当ThreadLocal的强引用释放掉后，通过一次系统gc检查，发现ThreadLocal对象只有threadLocalMap中Entry的弱引用持有，此时根据弱引用的机制就会回收ThreadLocal对象，从而避免了内存泄露。当然ThreadLocal还有一些额外的保护措施。



```java
/**
 * 弱引用回收测试
 */
public class WeakReferenceDemo {

    public static WeakReference<String> weakReference1;
    public static WeakReference<String> weakReference2;

    public static void main(String[] args) {

        test1();
        //可以输出hello值，此时两个弱引用扔持有对象，而且未进行gc
        System.out.println("未进行gc时，只有弱引用指向value内存区域：" + weakReference1.get());

        //此时已无强一用执行"value"所在内存区域，gc时会回收弱引用
        System.gc();

        //此时输出都为nuill
        System.out.println("进行gc时，只有弱引用指向value内存区域：" + weakReference1.get());

    }

    public static void test1() {
        String hello = new String("value");

        weakReference1 = new WeakReference<>(hello);

        System.gc();
        //此时gc不会回收弱引用，因为字符串"value"仍然被hello对象强引用
        System.out.println("进行gc时，强引用与弱引用同时指向value内存区域：" + weakReference1.get());

    }
}
```

结果：

```java
进行gc时，强引用与弱引用同时指向value内存区域：value
未进行gc时，只有弱引用指向value内存区域：value
进行gc时，只有弱引用指向value内存区域：null
```

分析输出结果可以看出：

当有强引用指向value内存区域时，即使进行gc，弱引用也不会被释放，对象不回被回收。

当无强引用指向value内存区域是，此时进行gc，弱引用会被释放，对象将会执行回收流程。



### 引用队列

下面我们来简单地介绍下引用队列的概念。实际上，WeakReference类有两个构造函数：

```
//创建一个指向给定对象的弱引用``WeakReference(T referent) 
//创建一个指向给定对象并且登记到给定引用队列的弱引用``WeakReference(T referent, ReferenceQueue<? ``super` `T> q)
```

我们可以看到第二个构造方法中提供了一个ReferenceQueue类型的参数，通过提供这个参数，我们便把创建的弱引用对象注册到了一个引用队列上，这样当它被垃圾回收器清除时，就会把它送入这个引用队列中，我们便可以对这些被清除的弱引用对象进行统一管理。

