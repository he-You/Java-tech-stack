####特性
1 . 保证了不同线程对该变量操作的内存可见性;
2 . 禁止指令重排序

JMM主要就是围绕着如何在并发过程中如何处理原子性、可见性和有序性这3个特征来建立的，
通过解决这三个问题，可以解除缓存不一致的问题。而volatile跟可见性和有序性都有关。

1 . **原子性(Atomicity)**： Java中，对基本数据类型的读取和赋值操作是原子性操作，所谓原子性操作就是指这些操作是不可中断的，要做一定做完，要么就没有执行。
例:
```
i = 2;
j = i;
i++;
i = i + 1；
```

上面4个操作中，
i=2是读取操作，必定是原子性操作，
j=i你以为是原子性操作，其实吧，分为两步，一是读取i的值，然后再赋值给j,这就是2步操作了，称不上原子操作，
i++和i = i + 1其实是等效的，读取i的值，加1，再写回主存，那就是3步操作了。

所以上面的举例中，最后的值可能出现多种情况，就是因为满足不了原子性。
这么说来，只有简单的读取，赋值是原子操作，还只能是用数字赋值，用变量的话还多了一步读取变量值的操作。
有个例外是，虚拟机规范中允许对64位数据类型(long和double)，分为2次32为的操作来处理，但是最新JDK实现还是实现了原子操作的。
JMM只实现了基本的原子性，像上面i++那样的操作，**必须借助于synchronized和Lock来保证整块代码的原子性了**。
线程在释放锁之前，必然会把i的值刷回到主存的。

2 . 可见性(Visibility)：
说到可见性，Java就是利用volatile来提供可见性的。
当一个变量被volatile修饰时，那么对它的修改会立刻刷新到主存，当其它线程需要读取该变量时，会去内存中读取新值。而普通变量则不能保证这一点。
其实通过synchronized和Lock也能够保证可见性，线程在释放锁之前，会把共享变量值都刷回主存，但是synchronized和Lock的开销都更大。

3 . 有序性（Ordering）

JMM是允许编译器和处理器对指令重排序的，但是规定了as-if-serial语义，即不管怎么重排序，程序的执行结果不能改变。比如下面的程序段：
```
double pi = 3.14;    //A
double r = 1;        //B
double s= pi * r * r;//C
```
上面的语句，可以按照A->B->C执行，结果为3.14,但是也可以按照B->A->C的顺序执行，因为A、B是两句独立的语句，
而C则依赖于A、B，所以A、B可以重排序，但是C却不能排到A、B的前面。
JMM保证了重排序不会影响到单线程的执行，但是在多线程中却容易出问题。

JMM具备一些先天的有序性,即不需要通过任何手段就可以保证的有序性，通常称为happens-before原则。
<<JSR-133：Java Memory Model and Thread Specification>>
定义了如下happens-before规则：

- 程序顺序规则： 一个线程中的每个操作，happens-before于该线程中的任意后续操作
- 监视器锁规则：对一个线程的解锁，happens-before于随后对这个线程的加锁
- volatile变量规则： 对一个volatile域的写，happens-before于后续对这个volatile域的读
- 传递性：如果A happens-before B ,且 B happens-before C, 那么 A happens-before C
- start()规则： 如果线程A执行操作ThreadB_start()(启动线程B) ,  那么A线程的ThreadB_start()happens-before 于B中的任意操作
- join()原则： 如果A执行ThreadB.join()并且成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回。
- interrupt()原则： 对线程interrupt()方法的调用先行发生于被中断线程代码检测到中断事件的发生，可以通过Thread.interrupted()方法检测是否有中断发生
- finalize()原则：一个对象的初始化完成先行发生于它的finalize()方法的开始

第1条规则程序顺序规则是说在一个线程里，所有的操作都是按顺序的，但是在JMM里其实只要执行结果一样，是允许重排序的，这边的happens-before强调的重点也是单线程执行结果的正确性，但是无法保证多线程也是如此。
第2条规则监视器规则其实也好理解，就是在加锁之前，确定这个锁之前已经被释放了，才能继续加锁。
第3条规则，就适用到所讨论的volatile，如果一个线程先去写一个变量，另外一个线程再去读，那么写入操作一定在读操作之前。
第4条规则，就是happens-before的传递性。


从内存语义上来看

当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量刷新到主内存

当读一个volatile变量时，JMM会把该线程对应的本地内存置为无效，线程接下来将从主内存中读取共享变量。

####volatile的两点内存语义能保证可见性和有序性，但是能保证原子性吗？
要想保证原子性，只能借助于synchronized,Lock以及并发包下的atomic的原子操作类了，即对基本数据类型的 自增（加1操作），自减（减1操作）、以及加法操作（加一个数），减法操作（减一个数）进行了封装，保证这些操作是原子性操作。

####volatile底层的实现机制

如果把加入volatile关键字的代码和未加入volatile关键字的代码都生成汇编代码，会发现加入volatile关键字的代码会多出一个lock前缀指令。
lock前缀指令实际相当于一个内存屏障，内存屏障提供了以下功能:

1 . 重排序时不能把后面的指令重排序到内存屏障之前的位置 
2 . 使得本CPU的Cache写入内存 
3 . 写入动作也会引起别的CPU或者别的内核无效化其Cache，相当于让新写入的值对别的线程可见。

举例:
状态量标记，就如上面对flag的标记：
```java
public class TestVolatile{  
      int a = 0;
      volatile bool flag = false;
      
      public void write(){
          a = 2;              //1
          flag = true;        //2
      }
      
      public void multiply(){
          if (flag) {         //3
              int ret = a * a;//4
          }
      }   
}
```
这种对变量的读写操作，标记为volatile可以保证修改对线程立刻可见。比synchronized,Lock有一定的效率提升。

2.单例模式的实现，典型的双重检查锁定（DCL）
```java
class Singleton{
    private volatile static Singleton instance = null;
 
    private Singleton() {
 
    }
 
    public static Singleton getInstance() {
        if(instance==null) {
            synchronized (Singleton.class) {
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

复制代码这是一种懒汉的单例模式，使用时才创建对象，而且为了避免初始化操作的指令重排序，给instance加上了volatile。

