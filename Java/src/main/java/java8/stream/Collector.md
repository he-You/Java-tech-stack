一、概述
Collector是专门用来作为Stream的collect方法的参数的。
```
public interface Stream<T> extends BaseStream<T, Stream<T>> {
    <R, A> R collect(Collector<? super T, A, R> collector);
}
```
而Collectors是作为生产具体Collector的工具类。

二、Collector
Collector主要包含五个参数，它的行为也是由这五个参数来定义的，如下所示：
```
public interface Collector<T, A, R> {
    // supplier参数用于生成结果容器，容器类型为A
    Supplier<A> supplier();
    // accumulator用于消费元素，也就是归纳元素，这里的T就是元素，它会将流中的元素一个一个与结果容器A发生操作
    BiConsumer<A, T> accumulator();
    // combiner用于两个两个合并并行执行的线程的执行结果，将其合并为一个最终结果A
    BinaryOperator<A> combiner();
    // finisher用于将之前整合完的结果R转换成为A
    Function<A, R> finisher();
    // characteristics表示当前Collector的特征值，这是个不可变Set
    Set<Characteristics> characteristics();
}
```
Collector拥有两个of方法用于生成Collector实例，其中一个拥有上面所有五个参数，另一个四个参数，不包括finisher。
```
public interface Collector<T, A, R> {
    // 四参方法，用于生成一个Collector，T代表流中的一个一个元素，R代表最终的结果
    public static<T, R> Collector<T, R, R> of(Supplier<R> supplier,
                                              BiConsumer<R, T> accumulator,
                                              BinaryOperator<R> combiner,
                                              Characteristics... characteristics) {/*...*/}
    // 五参方法，用于生成一个Collector，T代表流中的一个一个元素，A代表中间结果，R代表最终结果，finisher用于将A转换为R                                          
    public static<T, A, R> Collector<T, A, R> of(Supplier<A> supplier,
                                                 BiConsumer<A, T> accumulator,
                                                 BinaryOperator<A> combiner,
                                                 Function<A, R> finisher,
                                                 Characteristics... characteristics) {/*...*/}                                              
}
```
Characteristics：这个特征值是一个枚举，拥有三个值：CONCURRENT（多线程并行），UNORDERED（无序），IDENTITY_FINISH（无需转换结果）。
其中四参of方法中没有finisher参数，所以必有IDENTITY_FINISH特征值。

三、Collectors
Collectors是一个工具类，是JDK预实现Collector的工具类，它内部提供了多种Collector，我们可以直接拿来使用，非常方便。

- toCollection
将流中的元素全部放置到一个集合中返回，这里使用Collection，泛指多种集合。
```
public class CollectorsTest {
    public static void toCollectionTest(List<String> list) {
        List<String> ll = list.stream().collect(Collectors.toCollection(LinkedList::new));
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        toCollectionTest(list);
    }
}
```
- toList
将流中的元素放置到一个列表集合中去。这个列表默认为ArrayList。
```
public class CollectorsTest {
    public static void toListTest(List<String> list) {
        List<String> ll = list.stream().collect(Collectors.toList());
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        toListTest(list);
    }
}
```
- toSet
将流中的元素放置到一个无序集set中去。默认为HashSet。
```
public class CollectorsTest {
    public static void toSetTest(List<String> list) {
        Set<String> ss = list.stream().collect(Collectors.toSet());
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        toSetTest(list);
    }
}
```
- joining
joining的目的是将流中的元素全部以字符序列的方式连接到一起，可以指定连接符，甚至是结果的前后缀。
```
public class CollectorsTest {
    public static void joiningTest(List<String> list){
        // 无参方法
        String s = list.stream().collect(Collectors.joining());
        System.out.println(s);
        // 指定连接符
        String ss = list.stream().collect(Collectors.joining("-"));
        System.out.println(ss);
        // 指定连接符和前后缀
        String sss = list.stream().collect(Collectors.joining("-","S","E"));
        System.out.println(sss);
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        joiningTest(list);
    }
}
```
执行结果：
```
1234567891101212121121asdaa3e3e3e2321eew
123-456-789-1101-212121121-asdaa-3e3e3e-2321eew
S123-456-789-1101-212121121-asdaa-3e3e3e-2321eewE
StringJoiner：这是一个字符串连接器，可以定义连接符和前后缀，正好适用于实现第三种joining方法。
```
- mapping
这个映射是首先对流中的每个元素进行映射，即类型转换，然后再将新元素以给定的Collector进行归纳。
```
public class CollectorsTest {
    public static void mapingTest(List<String> list){
        List<Integer> ll = list.stream().limit(5).collect(Collectors.mapping(Integer::valueOf,Collectors.toList()));
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        mapingTest(list);
    }
}
```
实例中截取字符串列表的前5个元素，将其分别转换为Integer类型，然后放到一个List中返回。

- collectingAndThen
该方法是在归纳动作结束之后，对归纳的结果进行再处理。
```
public class CollectorsTest {
    public static void collectingAndThenTest(List<String> list){
        int length = list.stream().collect(Collectors.collectingAndThen(Collectors.toList(),e -> e.size()));
        System.out.println(length);
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        collectingAndThenTest(list);
    }
}
```
执行结果为：
```
8
```
- counting
该方法用于计数。
```
public class CollectorsTest {
    public static void countingTest(List<String> list){
        long size = list.stream().collect(Collectors.counting());
        System.out.println(size);
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        countingTest(list);
    }
}
```
结果：
8
- minBy/maxBy
生成一个用于获取最小/最大值的Optional结果的Collector。
```
public class CollectorsTest {
    public static void maxByAndMinByTest(List<String> list){
        System.out.println(list.stream().collect(Collectors.maxBy((a,b) -> a.length()-b.length())));
        System.out.println(list.stream().collect(Collectors.minBy((a,b) -> a.length()-b.length())));
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        maxByAndMinByTest(list);
    }
}
```
执行结果为：

Optional[212121121]
Optional[123]
- summingInt/summingLong/summingDouble
生成一个用于求元素和的Collector，首先通过给定的mapper将元素转换类型，然后再求和。

参数的作用就是将元素转换为指定的类型，最后结果与转换后类型一致。
```
public class CollectorsTest {
    public static void summingTest(List<String> list){
        int i = list.stream().limit(3).collect(Collectors.summingInt(Integer::valueOf));
        long l = list.stream().limit(3).collect(Collectors.summingLong(Long::valueOf));
        double d = list.stream().limit(3).collect(Collectors.summingDouble(Double::valueOf));
        System.out.println(i +"\n" +l + "\n" + d);
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        summingTest(list);
    }
}
```
执行结果为：

1368
1368
1368.0
- averagingInt/averagingLong/averagingDouble
生成一个用于求元素平均值的Collector，首选通过参数将元素转换为指定的类型。

参数的作用就是将元素转换为指定的类型，求平均值涉及到除法操作，结果一律为Double类型。
```
public class CollectorsTest {
    public static void averagingTest(List<String> list){
        double i = list.stream().limit(3).collect(Collectors.averagingInt(Integer::valueOf));
        double l = list.stream().limit(3).collect(Collectors.averagingLong(Long::valueOf));
        double d = list.stream().limit(3).collect(Collectors.averagingDouble(Double::valueOf));
        System.out.println(i +"\n" +l + "\n" + d);
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        averagingTest(list);
    }
}
```
执行结果为：

456.0
456.0
456.0
- reducing
reducing方法有三个重载方法，其实是和Stream里的三个reduce方法对应的，二者是可以替换使用的，作用完全一致，也是对流中的元素做统计归纳作用。
```
public final class Collectors {
    // 无初始值的情况，返回一个可以生成Optional结果的Collector
    public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op) {/*...*/}
    // 有初始值的情况，返回一个可以直接产生结果的Collector
    public static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op) {/*...*/}
    // 有初始值，还有针对元素的处理方案mapper，生成一个可以直接产生结果的Collector，元素在执行结果操作op之前需要先执行mapper进行元素转换操作
    public static <T, U> Collector<T, ?, U> reducing(U identity,
                                    Function<? super T, ? extends U> mapper,
                                    BinaryOperator<U> op) {/*...*/}
}
```
实例：
```
public class CollectorsTest {
    public static void reducingTest(List<String> list){
        System.out.println(list.stream().limit(4).map(String::length).collect(Collectors.reducing(Integer::sum)));
        System.out.println(list.stream().limit(3).map(String::length).collect(Collectors.reducing(0, Integer::sum)));
        System.out.println(list.stream().limit(4).collect(Collectors.reducing(0,String::length,Integer::sum)));
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        reducingTest(list);
    }
}
```
Optional[13]
9
13


- groupingBy
这个方法是用于生成一个拥有分组功能的Collector，它也有三个重载方法：
```
public final class Collectors {
    // 只需一个分组参数classifier，内部自动将结果保存到一个map中，每个map的键为?类型（即classifier的结果类型），值为一个list，这个list中保存在属于这个组的元素。
    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(
            Function<? super T, ? extends K> classifier) {/*...*/}
    // 在上面方法的基础上增加了对流中元素的处理方式的Collector，比如上面的默认的处理方法就是Collectors.toList()
    public static <T, K, A, D>Collector<T, ?, Map<K, D>> groupingBy(
            Function<? super T, ? extends K> classifier,Collector<? super T, A, D> downstream) {/*...*/}
    // 在第二个方法的基础上再添加了结果Map的生成方法。
    public static <T, K, D, A, M extends Map<K, D>>
        Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier,
                                      Supplier<M> mapFactory,
                                      Collector<? super T, A, D> downstream) {/*...*/}
}
```
实例：
```
public class CollectorsTest {
    public static void groupingByTest(List<String> list){
        Map<Integer,List<String>> s = list.stream().collect(Collectors.groupingBy(String::length));
        Map<Integer,List<String>> ss = list.stream().collect(Collectors.groupingBy(String::length, Collectors.toList()));
        Map<Integer,Set<String>> sss = list.stream().collect(Collectors.groupingBy(String::length,HashMap::new,Collectors.toSet()));
        System.out.println(s.toString() + "\n" + ss.toString() + "\n" + sss.toString());
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        groupingByTest(list);
    }
}
```
执行结果为：

{3=[123, 456, 789], 4=[1101], 5=[asdaa], 6=[3e3e3e], 7=[2321eew], 9=[212121121]}
{3=[123, 456, 789], 4=[1101], 5=[asdaa], 6=[3e3e3e], 7=[2321eew], 9=[212121121]}
{3=[123, 456, 789], 4=[1101], 5=[asdaa], 6=[3e3e3e], 7=[2321eew], 9=[212121121]}

groupingBy方法还有并发版的groupingByConcurrent，功能基本一致，只是返回的Collector是并行的。

- partitioningBy
该方法将流中的元素按照给定的校验规则的结果分为两个部分，放到一个map中返回，map的键是Boolean类型，值为元素的列表List。

该方法有两个重载方法：
```
public final class Collectors {
    // 只需一个校验参数predicate
    public static <T>
        Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate) {/*...*/}
    // 在上面方法的基础上增加了对流中元素的处理方式的Collector，比如上面的默认的处理方法就是Collectors.toList()
    public static <T, D, A>
        Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate,
                                                        Collector<? super T, A, D> downstream) {/*...*/}
}
```
实例：
```
public class CollectorsTest {
    public static void partitioningByTest(List<String> list){
        Map<Boolean,List<String>> map = list.stream().collect(Collectors.partitioningBy(e -> e.length()>5));
        Map<Boolean,Set<String>> map2 = list.stream().collect(Collectors.partitioningBy(e -> e.length()>6,Collectors.toSet()));
        System.out.println(map.toString() + "\n" + map2.toString());
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        partitioningByTest(list);
    }
}
```
执行结果：

{false=[123, 456, 789, 1101, asdaa], true=[212121121, 3e3e3e, 2321eew]}
{false=[123, 456, 1101, 789, 3e3e3e, asdaa], true=[212121121, 2321eew]}
- toMap
toMap方法是根据给定的键生成器和值生成器生成的键和值保存到一个map中返回，键和值的生成都依赖于元素，可以指定出现重复键时的处理方案和保存结果的map。
```
public final class Collectors {
    // 指定键和值的生成方式keyMapper和valueMapper
    public static <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper) {/*...*/}
    // 在上面方法的基础上增加了对键发生重复时处理方式的mergeFunction，比如上面的默认的处理方法就是抛出异常
    public static <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper,
                                        BinaryOperator<U> mergeFunction) {/*...*/}
    // 在第二个方法的基础上再添加了结果Map的生成方法。
    public static <T, K, U, M extends Map<K, U>>
        Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper,
                                    BinaryOperator<U> mergeFunction,
                                    Supplier<M> mapSupplier) {/*...*/}
}
```
实例：
```
public class CollectorsTest {
    public static void toMapTest(List<String> list){
        Map<String,String> map = list.stream().limit(3).collect(Collectors.toMap(e -> e.substring(0,1),e -> e));
        Map<String,String> map1 = list.stream().collect(Collectors.toMap(e -> e.substring(0,1),e->e,(a,b)-> b));
        Map<String,String> map2 = list.stream().collect(Collectors.toMap(e -> e.substring(0,1),e->e,(a,b)-> b,HashMap::new));
        System.out.println(map.toString() + "\n" + map1.toString() + "\n" + map2.toString());
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        toMapTest(list);
    }
}
```
执行结果：

{1=123, 4=456, 7=789}
{a=asdaa, 1=1101, 2=2321eew, 3=3e3e3e, 4=456, 7=789}
{a=asdaa, 1=1101, 2=2321eew, 3=3e3e3e, 4=456, 7=789}
第一种方式中，如果不添加limit限制，就会抛出异常。

还有并发的版本：toConcurrentMap,同样三种重载方法，与toMap基本一致，只是它最后使用的map是并发Map:ConcurrentHashMap。

- summarizingInt/summarizingLong/summarizingDouble
这三个方法适用于汇总的，返回值分别是IntSummaryStatistics，LongSummaryStatistics，DoubleSummaryStatistics。

在这些返回值中包含有流中元素的指定结果的数量、和、最大值、最小值、平均值。所有仅仅针对数值结果。
```
public class CollectorsTest {
    public static void summarizingTest(List<String> list){
        IntSummaryStatistics intSummary = list.stream().collect(Collectors.summarizingInt(String::length));
        LongSummaryStatistics longSummary = list.stream().limit(4).collect(Collectors.summarizingLong(Long::valueOf));
        DoubleSummaryStatistics doubleSummary = list.stream().limit(3).collect(Collectors.summarizingDouble(Double::valueOf));
        System.out.println(intSummary.toString() + "\n" + longSummary.toString() + "\n" + doubleSummary.toString());
    }
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123","456","789","1101","212121121","asdaa","3e3e3e","2321eew");
        summarizingTest(list);
    }
}
```
执行结果：
```
IntSummaryStatistics{count=8, sum=40, min=3, average=5.000000, max=9}
LongSummaryStatistics{count=4, sum=2469, min=123, average=617.250000, max=1101}
DoubleSummaryStatistics{count=3, sum=1368.000000, min=123.000000, average=456.000000, max=789.000000}
最后我们可以从返回的汇总实例中获取到想要的汇总结果。
```