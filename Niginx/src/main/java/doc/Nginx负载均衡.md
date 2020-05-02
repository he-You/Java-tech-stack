### Nginx负载均衡

#### 一、介绍

`Nginx`仅仅是作为`Nginx Proxy反向代理`使用的，因为这个反向代理功能表现的效果是负载均衡集群的效果

普通负载均衡软件，例如大名鼎鼎的LVS，其实功能只是对请求数据包的转发（也可能会改写数据包）、传递。其中DR模式明显的特征是：从负载均衡下面的节点服务器来看，接收到的请求还是来自访问负载均衡器的客户端的真实用户。

而反向代理就不一样了，反向代理接收访问用户的请求后，会代理用户重新发起请求代理下的节点服务器，最后把数据返回给客户端用户，在节点服务器看来，访问的节点服务器的客户端用户就是反向代理服务器了，而非真实的网站访问用户。

一句话，LVS等的负载均衡是转发用户请求的数据包，而Nginx反向代理是接收用户的请求后，会代理用户重新发起请求代理下的节点服务器。



#### 二、负载均衡组件模块

实现Nginx负载均衡的组件主要有两个：

- ngx_http_upstream_module ： 负载均衡模块，可以实现网站的负载均衡功能及节点的健康检查
- ngx_http_proxy_module： proxy代理模块，用于把请求转发给服务器节点或upstream服务器池



1. upstream模块介绍

   upstream模块允许Nginx定义一组或多组节点服务器，使用时可以通过proxy_pass代理的方式把网站的请求发送到事先定义好的对应upstream组的名字上，具体的写法：proxy_pass http://server_pools

   其中server_pools就是一个upstream节点服务器组名字。

2. 配置案例

   ```
   upstream server_pools {
   server 192.168.0.223;   #这行标签和下行是等价的
   server 192.168.0.224:80 weight=1 max_fails=1 fail_timeout=10s;       #这行标签和上一行是等价的，此行多余的部分就是默认配置，不写也可以。
   server 192.168.0.225:80 weight=1 max_fails=2 fail_timeout=20s backup;
   server 最后面可以加很多参数
   }
   
   ```

3. upstream模块参数

   - server

   ​       负载后面的RS配置，可以是ip或域名

   - weight

     请求服务器的权重。默认是1，越大表示接受的请求比例越大。

   - max_fails

     nginx 尝试连接后端服务器注解的失败次数。这个数值需配合`proxy_net_upstream`，`fastcgi_next_upstream`和`memcached_next_upstream`这三个参数来使用的。

     当nginx接收后端服务器返回这三个参数定义的状态码时，会将这个请求转发给正常工作的后端服务器，例如404，502，503

   - fail_timeout

     在`max_fails`定义的失败次数后，距离下次检查的时间间隔，默认10s

   - backup

     热备配置，标志这台服务器作为备份服务器，若主服务器全部宕机了，就会向它转发请求

   - down

     表示这个服务器永不可用，可配合ip_hash使用

     ```
     upstream web_pools {
     server linux.example.com weight=5; 
     server 127.0.0.1:8080 max_fail=5 fail_timeout=10s;
     # 当5次连续检查失败后，间隔10s后重新检测。
     server linux.example.com:8080 backup; 
     # 指定备份服务器。作用：等上面服务器全部不可访问时就向它转发请求。
     }
     ```

4. http_proxy_module模块

   - proxy_pass指令介绍

     `proxy_pass`指令属于`ngx_http_proxy_module`模块，此模块可以将请求转发到另一台服务器。在实际的反向代理工作中，会通过`location`功能匹配指定的URI，然后把接收到的符合匹配`URI`的请求通过`proxy_pass`抛给定义好的`upstream`节点池。

   - 使用案例

     ```
     location /web/ {
     proxy_pass http://127.0.0.1/abc/;
     }
     ```

     案例中：将匹配URI为web的请求抛给http://127.0.0.1/abc/

   - http proxy模块参数

     - proxy_set_header

       设置http请求header项传给后端服务器节点。可以实现让代理端的服务器节点获取访问客户端用户的真实的IP地址

     - client_body_buffer_size

       用于执行客户端请求主题缓冲区大小

     - proxy_connect_timeout

       表示反向代理与藕断节点服务器连接的超时时间，即发起握手等候相应的超时时间

     - proxy_send_timeout

       表示代理后端服务器的数据回传时间，也就是说在规定时间之内，后端服务器必须传回所有时间，否则，Nginx将断开这个连接

     - proxy_read_timeout

       设置nginx从代理的后端服务器获取信息的时间，表示连接建立成功后，nginx等待后端服务器的响应时间，其实是nginx在后端排队等候处理的时间

     - proxy_buffer_size

       设置缓冲区大小，默认该缓冲区大小等于指令proxy_buffers设置的大小

     - proxy_buffers

       设置缓冲区的数量和大小，nginx从代理的后端服务器获取的响应信息，会放置在缓冲区

     - proxy_busy_buffers_size

       用于设置系统很忙时可以使用的proxy_buffers大小，官方推荐的大小为proxy_bufer*2

     - proxy_temp_file_write_size

       指定proxy缓存临时文件的大小