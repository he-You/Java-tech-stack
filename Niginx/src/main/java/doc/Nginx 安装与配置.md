### Nginx 安装与配置（基于Docker环境）

#### 一、安装步骤

1. docker search nginx
2. docker pull nginx:latest
3. docker run --name nginx-nginx -p 8080:80 -d nginx



#### 二、配置

1. 目录结构

![img](https://mmbiz.qpic.cn/mmbiz_png/cbCLgfJZibpoRp30D929y0qzM58icwqiaUOFwTttQQxHs2TPda7Dg7go2UM2w4dU8A2ks1UPSHUjpw7AlNibvcXp6w/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

2. 核心配置文件nginx.conf

   ```
   
   #user  nobody;
   worker_processes  1;
   
   #error_log  logs/error.log;
   #error_log  logs/error.log  notice;
   #error_log  logs/error.log  info;
   
   #pid        logs/nginx.pid;
   
   events {
       worker_connections  1024;
   }
   
   
   http {
       include       mime.types;
       default_type  application/octet-stream;
       sendfile        on;
       keepalive_timeout  65;
   
       server {
           listen       80;
           server_name  localhost;
   
           location / {
               root   html;
               index  index.html index.htm;
           }
           
           error_page   500 502 503 504  /50x.html;
           location = /50x.html {
               root   html;
           }
       }
   }
   ```

   根据以上配置文件可以看出，主要分成三个部分

   - 全局块

     从配置文件开始到evens块之间的内容，主要设置影响nginx服务器整体运行的配置指令主要有：用户（组）、允许生成的work process数、进程PID存放的路径、日志存放路径以及配置文件的引入

     ```
     #定义Nginx运行的用户和用户组
     
     user nginx nginx;
     
      
     
     #nginx进程数，建议设置为等于CPU总核心数
     
     worker_processes 8;
     
      
     
     #全局错误日志定义类型，[ debug | info | notice | warn | error | crit ]
     
     error_log /var/log/nginx/error.log info;
     
      
     
     #进程文件
     
     pid /var/run/nginx.pid;
     
      
     
     #nginxworker最大打开文件数，可设置为系统优化后的ulimit -n的结果
     
     worker_rlimit_nofile 65535;
     ```

   - events块

     ```
     events
     
     {
     
     #epoll模型是Linux 2.6以上内核版本中的高性能网络I/O模型，如果跑在FreeBSD上面，就用kqueue模型
     
     use epoll;
     
     
     
     #单个worker进程最大连接数（nginx最大连接数=worker连接数*进程数）
     
     worker_connections 65535;
     
     }
     ```

   - http块

     代理、缓存和日志的定义等绝大多数功能和第三方模块的配置都在这里。

     其中：分为http 全局块、server块

     - http

       ```
       http 
       
       {
       
       include mime.types; #nginx支持的媒体类型库文件
       
       default_type application/octet-stream; #默认媒体文件类型
       
       #charset utf-8; #默认编码
       
       server_names_hash_bucket_size 128; #服务器名字的hash表大小
       
       client_header_buffer_size 32k; #上传文件大小限制
       
       sendfile on; #开启高效文件传输模式，实现内核零拷贝
       
       autoindex off; #开启目录列表访问，适合下载服务器，默认关闭。
       
       keepalive_timeout 120; #长连接超时时间，单位是秒
       
       } 
       ```

     - server块，即虚拟主机配置

       ```
       server #网站配置区域
       
       {
       
          listen 80;     #默认监听80端口
       
           server_name www.heyou.com;    #提供服务的域名主机名
       
           location / { 
       
                   root html;   
       
                   #站点根目录（这里html是相对路径，默认网站根目录为：/usr/local/nginx/html）
       
                   index index.thml index.htm;  #默认首页文件，多个用空格分开
       
           }
       
           error_page 500 502 503 504  /50x.html;    #出现对应http状态码时，使用50x.html回应客户
       
           location = /50x.thml {
       
           root    html;     #指定对应目录
       
           }
       
       } 
       ```

       