### SpringBoot 整合重试机制

一、pom 依赖

```maven
    <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
         <groupId>org.springframework.retry</groupId>
         <artifactId>spring-retry</artifactId>
    </dependency>
```



二、在主类上加上@EnableRetry注解，表示启用重试机制

```Java
@SpringBootApplication
@MapperScan("com.heyou.springboot.dao")
@EnableEncryptableProperties
@EnableRetry
public class SpringbootApplication {

    public static void main(String[] args) {
        System.out.println("启动中....");
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
```

三、在 service 方法上加上：

```java
@Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
```

其中：

- value:表示当出现哪些异常时会触发重试机制
- maxAttempts:最大的重试次数（默认是 3）
- delay:表示重试的延迟时间（ms）
- multiplier表示上一次延时时间是这一次的倍数。

