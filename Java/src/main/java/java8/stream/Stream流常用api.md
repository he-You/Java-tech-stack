### 什么是 Stream？

Stream（流）是一个来自数据源的元素队列并支持聚合操作

元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
- 数据源:流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
- 聚合操作:类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
和以前的Collection操作不同， Stream操作还有两个基础的特征：

- Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 
如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
- 内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式,显式的在集合外部进行迭代，这叫做外部迭代。 
Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。

### 如何生成流
在 Java 8 中, 集合接口有两个方法来生成流：
- stream(): 为集合创建串行流。
- parallelStream(): 为集合创建并行流。

### 常用方法
- forEach

Stream 提供了新的方法 'forEach' 来迭代流中的每个数据。以下代码片段使用 forEach 输出了10个随机数：
```
Random random = new Random();
random.ints().limit(10).forEach(System.out::println);
```
- map

map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数：
```
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
// 获取对应的平方数
List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
```
- filter

filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串：
```
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
// 获取空字符串的数量
long count = strings.stream().filter(string -> string.isEmpty()).count();
```
- limit/skip

limit 方法用于获取指定数量的流。 skip 则是扔掉前 n 个元素。以下代码片段使用 limit 方法打印出 10 条数据：
```
Random random = new Random();
random.ints().limit(10).forEach(System.out::println);
```
- 排序（sorted/reversed/min/max/distinct）

sorted/reversed可以对集合中的所有元素进行顺序/逆序排序。max，min可以寻找出流中最大或者最小的元素，而distinct可以寻找出不重复的元素

```
//对一个集合进行排序
List<Integer> sortLists = new ArrayList<>();
sortLists.add(1);
sortLists.add(4);
sortLists.add(6);
sortLists.add(3);
sortLists.add(2);
List<Integer> afterSortLists = sortLists.stream().sorted((In1,In2)->In1-In2).collect(Collectors.toList());

//自然序列
List<Student> studentList1=studentList.stream().sorted().collect(Collectors.toList());
//逆序
List<Student> studentList2=studentList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
//根据年龄自然顺序
List<Student> studentList3=studentList.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
//根据年龄逆序
List<Student> studentList4=studentList.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
//打印
studentList4.forEach(student -> System.out.println("id is "+student.getId()+" ;name is "+student.getName()+";age is "+student.getAge()));

//得到其中长度最大的元素：
List<String> maxLists = new ArrayList<>();
maxLists.add("a");
maxLists.add("b");
maxLists.add("c");
maxLists.add("d");
maxLists.add("e");
maxLists.add("f");
maxLists.add("hahaha");
int maxLength = maxLists.stream().mapToInt(s->s.length()).max().getAsInt();
System.out.println("字符串长度最长的长度为"+maxLength);
//对一个集合进行查重
List<String> distinctList = new ArrayList<>();
distinctList.add("a");
distinctList.add("a");
distinctList.add("c");
distinctList.add("d");
List<String> afterDistinctList = distinctList.stream().distinct().collect(Collectors.toList());
```

- 匹配(Match方法)
有的时候，我们只需要判断集合中是否全部满足条件，或者判断集合中是否有满足条件的元素，这时候就可以使用match方法：
1) allMatch：Stream 中全部元素符合传入的 predicate，返回 true
2) anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
3) noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
### 并行（parallel）程序
parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
```
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
// 获取空字符串的数量
int count = strings.parallelStream().filter(string -> string.isEmpty()).count();
```
我们可以很容易的在顺序运行和并行直接切换。

Collectors

Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
```
List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
System.out.println("筛选列表: " + filtered);
String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
System.out.println("合并字符串: " + mergedString);
```
统计
另外，一些产生统计结果的收集器也非常有用。它们主要用于int、double、long等基本类型上，它们可以用来产生类似如下的统计结果。
```
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
 
IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

System.out.println("列表中最大的数 : " + stats.getMax());
System.out.println("列表中最小的数 : " + stats.getMin());
System.out.println("所有数之和 : " + stats.getSum());
System.out.println("平均数 : " + stats.getAverage());
```