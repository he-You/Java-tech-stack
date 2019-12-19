###Redis数据结构
一.基础数据结构
1.Redis的基础数据结构主要有:
**String/Hash/List/Set/SortedSet**

1.1 String:是简单的key-value类型，value其实不仅可以是String，也可以是数字。 
 常规key-value缓存应用,value最大可以存放512M;二进制安全(可以包含任何对象,图片或序列化的对象)
 - set key "value" :向redis中存放(覆盖)键为key,值为value的string类型的键值对
 - get key :通过key获取value
 - mget k1,k2...:获取所有包含k1,k2...的键值对,返回一个包含所有给定 key 的值的列表。
 
 1.2 Hash:String元素组成的字典,适合用于存放对象
- hset: HMSET object name "redis-Hash-object" description "redis basic commands for caching" likes 20 visitors 23000
- hget :hget object name
- hgetall

1.3 List:列表,按照String元素插入顺序排序,你可以添加一个元素到列表的头部（左边）或者尾部（右边）
 一个列表最多可以包含 2^32 - 1 个元素 (4294967295, 每个列表超过40亿个元素)。Redis list 的实现为一个双向链表，即可以支持反向查找和遍历，更方便操作，不过带来了部分额外的内存开销。
另外可以通过 lrange 命令，就是从某个元素开始读取多少个元素，可以基于 list 实现分页查询，这个很棒的一个功能，基于 redis 实现简单的高性能分页
 - Lpush/Rpush:左添加/右添加
 - Lpop/Rpop :左删除/右删除
 - Lrange: lrange index1 index2
 
 1.4 set:String元素组成的无序不重复的集合,通过Hash表实现(添加删除查询的时间复杂度都是O(1)) 
 set 对外提供的功能与list类似是一个列表的功能，特殊之处在于 set 是可以自动去重的。
 当你需要存储一个列表数据，又不希望出现重复数据时，set是一个很好的选择，并且set提供了判断某个成员是否在一个set集合内的重要接口，这个也是list所不能提供的。可以基于 set 轻易实现交集、并集、差集的操作。
 - sadd
 - spop
 - smembers 无序打印集合中的元素
 - sunion
 
 1.5 sorted set,Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。    
不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。
有序集合的成员是唯一的,但分数(score)却可以重复。
集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。 集合中最大的成员数为 2^32 - 1  
- zadd :zadd key1 sorce1 value1
- zrange
- zrem 
- zcard:获取有序集合的成员数
- Zinterstore :算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys(key的个数)参数指定，并将该交集(结果集)储存到 destination 。


2.特殊的数据结构:
- HyperLogLog、Geo、Pub/Sub
- Redis module:BloomFilter，RedisSearch，Redis-ML


