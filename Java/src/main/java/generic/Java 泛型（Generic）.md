### Java 泛型（Generic）

泛型类型本质上是定义泛型的类类型，只不过在编译期间根据传入的参数类型推导成指定的类型,所以在定义的过程中泛型无法完成重载；

#### 一、泛型类与泛型接口

泛型类/接口的定义，使用一个类型变量T（其他大写字母都可以，不过常用的就是T，E，K，V等等），相当于一个占位符，并且用<>括起来，并放在类/接口名的后面。泛型类是允许有多个类型变量的。泛型类型不支持基本数据类型。

```Java
class A<T>{
    private T t;
    public T get(){ return t;}
    public void set(T t){}
}

//泛型类实现接口：未填充接口的泛型，是泛型类
class A<T> implement B<T>{
    public T get(){}
    public void set(T t){}
}


//非泛型类实现接口：填充接口的泛型，类已不是泛型类
class A implement B<String>{
    public Stirng get(){}
    public void set(String str){}
}


//多泛型类实现接口，是泛型类
class A<T,V> implement B<T>{
    public T get(){}
    public void set(T t){}
    
    public V get(){}
    public void set(V v){}
}

//多泛型类实现接口，是泛型类
class A<V> implement B<String>{
    public Stirng get(){}
    public void set(String str){}
    
    public V get(){}
    public void set(V v){}
}

```



实现发现接口的类有两种方式：

- 创建对象时传入具体的类型

```java
public class Generics<T> implements ImpGenerics<T> {
}
```

- 实现接口的时候就固定具体的实参类型

```Java
public class Generics implements ImpGenerics<String> {
}
```



#### 二、泛型方法

泛型方法是在调用方法的时候指明泛型的具体类型 ，泛型方法可以在任何地方和任何场景中使用，包括普通类和泛型类

```java
public class GenericsClass<K,V> {
    private K date;
    private V value;
    //普通方法
    private V getValue(K date){
        return value;
    }

    //泛型方法
    private <T> T genericsMethod(T date){
        return date;
    }

    public GenericsClass(K date, V value) {
        this.date = date;
        this.value = value;
    }

    private void print(){
        System.out.println("泛型类型");
    }
    public static void main(String [] args){
        GenericsClass<String,Integer> genericsClass = new GenericsClass<>("k",123);
        genericsClass.print();
        int a = genericsClass.getValue("k");
        System.out.println("v="+a);
    }
}

```

**注：**

1. 泛型方法必须通过“<类型占位符>”来声明返回的类型，譬如<V>等
2. 泛型类的成员方法不能声明为静态方法，而泛型方法可以声明称静态方法。

#### 三、通配符

类型通配符：一般是用"？"代替具体的类型实参，所以类型通配符是类型实参而不是类型形参

1. 概念：

有时候希望传入的类型不在局限于某个单一类型，而是有一个指定的范围，从而可以进行一些特定的操作，这时候就需要使用通配符边界了

2. 类型

通配符有两种：

- <? extends X> 表示类型的上界，类型(实)参数是X的子类或自身；注意采用上限通配符的泛型集合是无法进行填充元素的，因为不知道集合中要存放何种类型的元素
- <? super X> 表示类型的下界，类型(实)参数是X的超类;集合可以填充元素，但是不保证元素的属性要求

------

3. **PECS（producer-extends, costumer-super）原则**

要在表示 生产者或者消费者 的输入参数上使用通配符，使用的规则就是：生产者有上限、消费者有下限

#### 四、Java 泛型原理

Java语言中的泛型一般称为伪泛型，它**只在程序源码中存在**，在编译后的字节码文件中，就已经替换为原来的原生类型（Raw Type，也称为裸类型）了，并且在相应的地方插入了强制转型代码，因此，对于运行期的Java语言来说，ArrayList＜int＞与ArrayList＜String＞就是同一个类，所以泛型技术实际上是Java语言的一颗语法糖，Java语言中的泛型实现方法称为类型擦除，基于这种方法实现的泛型称为伪泛型

由于Java泛型的引入，各种场景（虚拟机解析、反射等）下的方法调用都可能对原有的基础产生影响和新的需求，如在泛型类中如何获取传入的参数化类型等。

因此，JCP组织对虚拟机规范做出了相应的修改，引入了诸如Signature、LocalVariableTypeTable等新的属性用于解决伴随泛型而来的参数类型的识别问题，Signature是其中最重要的一项属性，它的作用就是存储一个方法在字节码层面的特征签名[3]，这个属性中保存的参数类型并不是原生类型，而是包括了参数化类型的信息。修改后的虚拟机规范要求所有能识别49.0以上版本的Class文件的虚拟机都要能正确地识别Signature参数。 

另外，从Signature属性的出现我们还可以得出结论，擦除法所谓的擦除，仅仅是对方法的Code属性中的字节码进行擦除，实际上元数据中还是保留了泛型信息，这也是我们能通过反射手段取得参数化类型的根本依据



