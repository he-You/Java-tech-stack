### Nginx中的location指令

#### 一、介绍

location是Nginx的块级指令，其功能是用来匹配不同的url请求，进而对请求做出不同的处理和响应。



#### 二、语法

1. location有两种匹配规则：

- 匹配URL类型，有四种参数可选，当然也可以不带参数。

  location [ = | ~ | ~* | ^~ ] uri { … }

- 命名location,用@标识：location @name { … }

2. 参数解释

- "="，精确匹配，内容要同表达式完全一致才匹配成功

```
location = /abc/ {
  .....
 }
        
# 只匹配http://abc.com/abc
#http://abc.com/abc [匹配成功]
#http://abc.com/abc/index [匹配失败]
```



- "~",执行正则匹配，区分大小写

```

location ~ /Abc/ {
  .....
}
#http://abc.com/Abc/ [匹配成功]
#http://abc.com/abc/ [匹配失败]
```



- "~*",执行正则匹配，不区分大小写

```

location ~*/Abc/ {
  .....
}
#http://abc.com/Abc/ [匹配成功]
#http://abc.com/abc/ [匹配成功]
```



- "^~",表示普通字符串上以后不再进行正则匹配

```
location ^~ /index/ {
  .....
}
#以 /index/ 开头的请求，都会匹配上
#http://abc.com/index/index.page  [匹配成功]
#http://abc.com/error/error.page [匹配失败]
```



- 不加任何规则：默认是大小写敏感的，前缀匹配，相当于加了"~"与"^~"

```
location /index/ {
  ......
}
#http://abc.com/index  [匹配成功]
#http://abc.com/index/index.page  [匹配成功]
#http://abc.com/test/index  [匹配失败]
#http://abc.com/Index  [匹配失败]
# 匹配到所有uri
```



- "@"Nginx内部跳转

```
location /index/ {
  error_page 404 @index_error;
}
location @index_error {
  .....
}
#以 /index/ 开头的请求，如果链接的状态为 404。则会匹配到 @index_error 这条规则上。
```



3. 匹配顺序

  =` > `^~` > `~ | ~*` > `最长前缀匹配` > `/

#### 序号越小优先级越高

1. location =   # 精准匹配
2. location ^~  # 带参前缀匹配
3. location ~   # 正则匹配（区分大小写）
4. location ~*  # 正则匹配（不区分大小写）
5. location /a  # 普通前缀匹配，优先级低于带参数前缀匹配。
6. location /   # 任何没有匹配成功的，都会匹配这里处理



4. Location URI结尾不带/

- 如果 URI 结构是 https://domain.com/ 的形式，尾部有没有 / 都不会造成重定向。因为浏览器在发起请求的时候，默认加上了 / 。
- 如果 URI 的结构是 https://domain.com/some-dir/ 。尾部如果缺少 / 将导致重定向。因为**约定，URL 尾部的 / 表示目录，没有 / 表示文件**。所以访问 /some-dir/ 时，服务器会自动去该目录下找对应的默认文件。如果访问 /some-dir 的话，服务器会先去找 some-dir 文件，找不到的话会将 some-dir 当成目录，重定向到 /some-dir/ ，去该目录下找默认文件。

注：如果需要对这两种请求做不同的处理，就需要明确location配置：

```
location  /doc {
  proxy_pass http://www.doc123.com
}
location  /doc/ {
  proxy_pass http://www.doc456.com
}
```



