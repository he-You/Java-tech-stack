Optional类的作用:解决NPE问题,即NullPointerException

Optional 是一个简单的容器，其值可能是null或者不是null。
在Java 8之前一般某个函数应该返回非空对象但是有时却什么也没有返回，
而在Java 8中，你应该返回 Optional 而不是 null。

Optional 的三种构造方式:
- Optional.of(obj)
- Optional.ofNullable(obj) 
- Optional.empty()

Optional.of(obj): 它要求传入的 obj 不能是 null 值的, 否则还没开始进入角色就倒在了 NullPointerException 异常上了.

Optional.ofNullable(obj): 它以一种智能的, 宽容的方式来构造一个 Optional 实例. 来者不拒, 传 null 进到就得到 Optional.empty(), 非 null 就调用 Optional.of(obj).
那是不是我们只要用 Optional.ofNullable(obj) 一劳永逸, 以不变应二变的方式来构造 Optional 实例就行了呢? 那也未必, 否则 Optional.of(obj) 何必如此暴露呢, 私有则可?

#####**使用Optional.of(obj)原则**
- 当我们非常明确将要传给 Optional.of(obj) 的 obj 参数不可能为 null 时, 比如它是一个刚 new 出来的对象(Optional.of(new User(...))), 
或者是一个非 null 常量时; 
- 当想为 obj 断言不为 null 时, 即我们想在万一 obj 为 null 立即报告 NullPointException 异常, 立即修改, 而不是隐藏空指针异常时, 
我们就应该果断的用 Optional.of(obj) 来构造 Optional 实例, 而不让任何不可预计的 null 值有可乘之机隐身于 Optional 中.

以下为Optional<T>的正确使用方式：
- 存在即返回, 无则提供默认值
```
return user.orElse(null);  //而不是 return user.isPresent() ? user.get() : null;
return user.orElse(UNKNOWN_USER);
```
- 存在即返回, 无则由函数来产生
```
return user.orElseGet(() -> fetchAUserFromDatabase()); //而不要 return user.isPresent() ? user: fetchAUserFromDatabase();
```
- 存在才对它做点什么
```
user.ifPresent(System.out::println;);

//而不要下边那样
if (user.isPresent()) {
  System.out.println(user.get());
}
```

#####**map**
当 user.isPresent() 为真, 获得它关联的 orders的映射集合, 为假则返回一个空集合时, 
我们用上面的 orElse, orElseGet 方法都乏力时, 那原本就是 map 函数的责任, 我们可以这样一行
```
return user.map(u -> u.getOrders()).orElse(Collections.emptyList())

//上面避免了我们类似 Java 8 之前的做法
if(user.isPresent()) {
  return user.get().getOrders();
} else {
  return Collections.emptyList();
}
```
map 是可能无限级联的, 比如再深一层, 获得用户名的大写形式
```
return user.map(u -> u.getUsername())
           .map(name -> name.toUpperCase())
           .orElse(null);
```
这要搁在以前, 每一级调用的展开都需要放一个 null 值的判断
```
User user = .....
if(user != null) {
  String name = user.getUsername();
  if(name != null) {
    return name.toUpperCase();
  } else {
    return null;
  }
} else {
  return null;
}
```
- filter() :如果有值并且满足条件返回包含该值的Optional，否则返回空Optional。
```
Optional<String> longName = name.filter((value) -> value.length() > 6);  
System.out.println(longName.orElse("The name is less than 6 characters"));//输出Sanaulla  
```
- flatMap() :如果有值，为其执行mapping函数返回Optional类型返回值，否则返回空Optional。
flatMap与map（Funtion）方法类似，区别在于flatMap中的mapper返回值必须是Optional。调用结束时，flatMap不会对结果用Optional封装。
flatMap方法与map方法类似，区别在于mapping函数的返回值不同。map方法的mapping函数返回值可以是任何类型T，而flatMap方法的mapping函数必须是Optional。
```
upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));  
System.out.println(upperName.orElse("No value found"));//输出SANAULLA  
```
orElseThrow() 在有值时直接返回, 无值时抛出想要的异常.


