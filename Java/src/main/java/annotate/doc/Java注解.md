### Java注解

#### 零、注解介绍

​		注解又被称之为标注，一种Java元数据，Java注解可以通过反射获取标注的内容，在编译生成字节码文件时嵌入字节码中，虚拟机可以保留注解内容，运行期间可以获取到注解内容。

#### 一、内置注解

Java 定义了一套注解，共有 7 个，3 个在 java.lang 中，剩下 4 个在 java.lang.annotation 中。

1. 作用在代码的注解是

- `@Override` - 检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。
- `@Deprecated` - 标记过时方法。如果使用该方法，会报编译警告。
- `@SuppressWarnings` - 指示编译器去忽略注解中声明的警告。

2. 作用在其他注解的注解(或者说**元注解**)是:

- `@Retention` - 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问。
- `@Documented` - 标记这些注解是否包含在用户文档中。
- `@Target` - 标记这个注解应该是哪种 Java 成员。
- `@Inherited` - 标记这个注解是继承于哪个注解类(默认 注解并没有继承于任何子类)

3. 从 Java 7 开始，额外添加了 3 个注解:

- `@SafeVarargs` - Java 7 开始支持，忽略任何使用参数为泛型变量的方法或构造函数调用产生的警告。
- `@FunctionalInterface` - Java 8 开始支持，标识一个匿名函数或函数式接口。
- `@Repeatable` - Java 8 开始支持，标识某注解可以在同一个声明上使用多次。

#### 二、元注解

1. **@Retention**

`@Retention` annotation指定标记注解的存储方式或者说保留时长：

- RetentionPolicy.SOURCE - 注解仅存在于源码中，在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码。
- RetentionPolicy.CLASS - 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得。
- RetentionPolicy.RUNTIME - 注解会在class字节码文件中存在，在运行时可以通过反射获取到。

2. **@Documented**

`@Documented` 注释表明，无论何时使用指定的注释，都应使用Javadoc工具记录这些元素。（默认情况下，注释不包含在Javadoc中。）有关更多信息，请参阅 Javadoc工具页面。

3. **@Target**

`@Target` 注释标记另一个注释，以限制可以应用注释的Java元素类型。目标注释指定以下元素类型之一作为其值

| 元素类型                    | 说明                                         |
| --------------------------- | -------------------------------------------- |
| ElementType.TYPE            | 可以应用于类的任何元素：接口、类、枚举、注解 |
| ElementType.FIELD           | 可以应用于字段或属性：字段、枚举的常量       |
| ElementType.METHOD          | 可以应用于方法级注释                         |
| ElementType.PARAMETER       | 可以应用于方法的参数                         |
| ElementType.CONSTRUCTOR     | 可以应用于构造函数                           |
| ElementType.LOCAL_VARIABLE  | 可以应用于局部变量                           |
| ElementType.ANNOTATION_TYPE | 可以应用于注解类型                           |
| ElementType.PACKAGE         | 可以应用于包声明                             |
| ElementType.TYPE_PARAMETER  | 表示该注解能写在类型变量的声明语句中         |
| ElementType.TYPE_USE        | 表示该注解能写在使用类型的任何语句中         |

4. **@Inherited**

`@Inherited` 表示注解可以被子类继承。当用户查询注解类型并且该类没有此类型的注解时，将查询类的超类以获取注解类型（默认情况下不是这样）。此注解仅适用于类声明。

5. **@Repeatable**

Repeatable Java SE 8中引入的，`@Repeatable`注解表明标记的注解可以多次应用于相同的声明或类型使用(即可以重复在同一个类、方法、属性等上使用)。

#### 三、自定义注解

1. 创建

   自定义注解的格式是以@interface为标志的。

```Java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Check {
    /**
     * default extension name
     */
    String value() default "";
}
```

