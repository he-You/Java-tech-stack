package SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author heyou
 * @date 2020/2/17 20:53
 */
@SpringBootApplication
public class SpringbootApplication {
    /**
     * 默认启动方式
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class,args);
    }
    /**
     * 不依赖内建的Tomcat启动,使用外部Tomcat服务器
     */
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(SpringbootApplication.class);
    }

}
