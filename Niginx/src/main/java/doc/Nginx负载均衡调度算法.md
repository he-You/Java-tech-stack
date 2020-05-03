### Nginx负载均衡调度算法

1. **rr轮询**

   这是nginx的默认调度算法，按照客户端请求逐一分配到不同的后端服务器，宕机的服务器会自动从节点服务器池中剔除。

     ```
 upstream server_pools {
    server 192.168.1.251;
    server 192.168.1.252;
    }
     ```

注：如果服务器集群性能不同，该调度算法容易引发资源分配不合理等问题。

2. **wrr加权轮询**

   该算法是基于默认轮询算法的基础上加上权重，权重越大，被转发的请求也越多。

```
upstream server_pools {
    server 192.168.1.251 weight=5;
    server 192.168.1.252 weight=10;
}
```

注：与默认轮询相比，加权轮询在集群服务器性能存在差异的情况下，能够使资源分配更加合理化。

3. **ip_hash(会话保持)**

   每个请求按访问ip的hash结果分配，每个访客固定一个后端服务器，可以解决session不共享的问题。

```
upstream server_pools {
    ip_hash;
    server 192.168.1.251;
    server 192.168.1.252;
    }
```

注：所谓的Session 不共享是说，假设用户已经登录过，此时发出的请求被分配到了 A 服务器，但 A 服务器突然宕机，用户的请求则会被转发到 B 服务器。但由于 Session 不共享，B 无法直接读取用户的登录信息来继续执行其他操作。

4. **fair动态调度算法**

   根据后端节点服务器的响应时间来分配请求，响应时间短的优先分配。

```
upstream server_pools {
    server 192.168.1.251;
    server 192.168.1.252;
    fair;
    }
```

注：Nginx本身不支持fair调度算法，需要Nginx中的upstream_fair模块支持。

5. url_hash算法

   根据访问URL的hash结果来分配请求，让每个URL定向到同一个后端服务器。

```
upstream server_pools {
server qll:9001;
server qll:9002;
hash $request_uri;
hash_method crc32;
}
```

注：Nginx本身是不支持url_hash，如果需要使用这种调度算法，必须安装Nginx的hash模块软件包。



