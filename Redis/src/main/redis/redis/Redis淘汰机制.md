redis 设置过期时间

Redis中有个设置时间过期的功能，即对存储在 redis 数据库中的值可以设置一个过期时间。
作为一个缓存数据库，这是非常实用的。如我们一般项目中的 token 或者一些登录信息，尤其是短信验证码都是有时间限制的，
按照传统的数据库处理方式，一般都是自己判断过期，这样无疑会严重影响项目性能。

我们 set key 的时候，都可以给一个 expire time，就是过期时间，
通过过期时间我们可以指定这个 key 可以存活的时间。

如果假设你设置了一批 key 只能存活1个小时，那么接下来1小时后，redis是怎么对这批key进行删除的？

定期删除+惰性删除。

通过名字大概就能猜出这两个删除方式的意思了。

定期删除：redis默认是每隔 100ms 就随机抽取一些设置了过期时间的key，检查其是否过期，如果过期就删除。
注意这里是随机抽取的。为什么要随机呢？你想一想假如 redis 存了几十万个 key ，
每隔100ms就遍历所有的设置过期时间的 key 的话，就会给 CPU 带来很大的负载！

惰性删除 ：定期删除可能会导致很多过期 key 到了时间并没有被删除掉。所以就有了惰性删除。
假如你的过期 key，靠定期删除没有被删除掉，还停留在内存里，除非你的系统去查一下那个 key，才会被redis给删除掉。
这就是所谓的惰性删除.
但是仅仅通过设置过期时间还是有问题的。

我们想一下：如果定期删除漏掉了很多过期 key，然后你也没及时去查，也就没走惰性删除，此时会怎么样？
如果大量过期key堆积在内存里，导致redis内存块耗尽了。怎么解决这个问题呢？ 

redis 内存淘汰机制。

如何保证Redis中的数据为热点数据?

在Redis中最大内存的设置是通过设置maxmemory来完成的，格式为maxmemory bytes ,
当目前使用的内存超过了设置的最大内存，就要进行内存释放了， 当需要进行内存释放的时候，
需要用某种策略对保存的的对象进行删除。Redis有六种策略（默认的策略是volatile-lru。）

Redis内存淘汰策略:
- volatile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰
- volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰
- volatile-random：从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰
- allkeys-lru：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的key（这个是最常用的）
- allkeys-random：从数据集（server.db[i].dict）中任意选择数据淘汰
- no-eviction：禁止驱逐数据，也就是说当内存不足以容纳新写入数据时，新写入操作会报错。这个应该没人使用吧！

4.0版本后增加以下两种：
- volatile-lfu：从已设置过期时间的数据集(server.db[i].expires)中挑选最不经常使用的数据淘汰
- allkeys-lfu：当内存不足以容纳新写入数据时，在键空间中，移除最不经常使用的key

除此之外还有一个配置项，就是**maxmemory-samples**，默认值是3，因为上面的策略代码实现的都是近似算法，所以不管是lru算法，还是ttl，都并不是在数据库中所有的数据为基础的算法，因为当数据库的数据很多的时候，这样效率太低，所以代码中都是基于maxmemory-samples个数据的近似算法。详情请读下文。
置换策略是如何工作的：
1）客户端执行一条新命令，导致数据库需要增加数据（比如set key value）

2）Redis会检查内存使用，如果内存使用超过maxmemory，就会按照置换策略删除一些key

3）新的命令执行成功

注意：
如果我们持续的写数据会导致内存达到或超出上限maxmemory，但是置换策略会将内存使用降低到上限以下。
如果一次需要使用很多的内存（比如一次写入一个很大的set），那么，Redis的内存使用可能超出最大内存限制一段时间。

内存释放机制原理：

概述：

当mem_used内存已经超过maxmemory的设定，对于所有的读写请求，
都会触发redis.c/freeMemoryIfNeeded(void)函数以清理超出的内存。
注意这个**清理过程是阻塞的**，直到清理出足够的内存空间。
所以如果在达到maxmemory并且调用方还在不断写入的情况下，可能会反复触发主动清理策略，导致请求会有一定的延迟。
清理时会根据用户配置的maxmemory-policy来做适当的清理（一般是LRU或TTL），
这里的LRU或TTL策略并不是针对redis的所有key，而是以配置文件中的maxmemory-samples个key作为样本池进行抽样清理。
maxmemory-samples在redis-3.0.0中的默认配置为5，如果增加，会提高LRU或TTL的精准度，
redis作者测试的结果是当这个配置为10时已经非常接近全量LRU的精准度了，
并且增加maxmemory-samples会导致在主动清理时消耗更多的CPU时间，有如下建议：

1）尽量不要触发maxmemory，最好在mem_used内存占用达到maxmemory的一定比例后，需要考虑调大hz以加快淘汰，或者进行集群扩容。

2）如果能够控制住内存，则可以不用修改maxmemory-samples配置；如果Redis本身就作为LRU cache服务（这种服务一般长时间处于maxmemory状态，由Redis自动做LRU淘汰），可以适当调大maxmemory-samples。