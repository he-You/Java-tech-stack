package sourcecode.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sourcecode.config.AppConfig;
import sourcecode.cycledepends.X;

/**
 * @author heyou
 * @date 2020/2/19 21:18
 * @description 测试循环依赖
 */
public class TestCycleDepends {
    public static void main(String[] args) {
        //初始化Spring容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        applicationContext.getBean(X.class);
    }
}
