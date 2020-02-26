### SpringBoot的启动过程

以下为SpringBoot的启动流程图

![](../spring/img/SpringBoot启动流程图.png)

我们发现启动流程主要分为三个部分:
- 第一部分进行SpringApplication的初始化模块，配置一些基本的环境变量、资源、构造器、监听器
- 第二部分实现了应用具体的启动方案，包括启动流程的监听模块、加载配置环境模块、及核心的创建上下文环境模块，
- 第三部分是自动化配置模块，该模块作为springboot自动配置核心

####启动
每个SpringBoot程序都有一个主入口，也就是main方法，
main里面调用SpringApplication.run()启动整个spring-boot程序，
该方法所在类需要使用@SpringBootApplication注解，
以及@ImportResource注解(if need)，@SpringBootApplication包括三个注解，
功能如下：
- @EnableAutoConfiguration：SpringBoot根据应用所声明的依赖来对Spring框架进行自动配置

- @SpringBootConfiguration(内部为@Configuration)：被标注的类等于在spring的XML配置文件中(applicationContext.xml)，装配所有bean事务，提供了一个spring的上下文环境

- @ComponentScan：组件扫描，可自动发现和装配Bean，默认扫描SpringApplication的run方法里的Booter.class所在的包路径下文件，所以最好将该启动类放到根包路径下

其中run()方法是个静态方法
                
```java
/**
	 * Static helper that can be used to run a {@link SpringApplication} from the
	 * specified source using default settings.
	 * @param primarySource the primary source to load
	 * @param args the application arguments (usually passed from a Java main method)
	 * @return the running {@link ApplicationContext}
	 */
	public static ConfigurableApplicationContext run(Class<?> primarySource,
			String... args) {
		return run(new Class<?>[] { primarySource }, args);
	}

	/**
	 * Static helper that can be used to run a {@link SpringApplication} from the
	 * specified sources using default settings and user supplied arguments.
	 * @param primarySources the primary sources to load
	 * @param args the application arguments (usually passed from a Java main method)
	 * @return the running {@link ApplicationContext}
	 */
	public static ConfigurableApplicationContext run(Class<?>[] primarySources,
			String[] args) {
		return new SpringApplication(primarySources).run(args);
	}
```
最后会调用创建SpringApplication实例并run
```java
public SpringApplication(Class... primarySources) {
    this((ResourceLoader)null, primarySources);
}

public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    this.resourceLoader = resourceLoader;
    Assert.notNull(primarySources, "PrimarySources must not be null");
    this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
    this.webApplicationType = WebApplicationType.deduceFromClasspath();
    //实例化初始容器
    setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
    //实例化监听器
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    this.mainApplicationClass = deduceMainApplicationClass();
}
```
SpringApplication的构造函数实例化了初始化上下文的各种接口
**ApplicationContextInitializer**以及监听器**ApplicationListener**，要注意的是这里的实例化，并不像平时的Spring Components一样通过注解和扫包完成，
而是通过一种不依赖Spring上下文的加载方法，这样才能在Spring完成启动前做各种配置。Spring的解决方法是以接口的全限定名作为key，
实现类的全限定名作为value记录在项目的META-INF/spring.factories文件中，然后通过SpringFactoriesLoader工具类提供静态方法进行类加载并缓存下来，
spring.factories是Spring Boot的核心配置文件，后面会继续说明。
另外比较有意思的是两个deduce方法，Spring Boot项目主要的目标之一就是自动化配置，通过这两个deduce方法可以看出，
Spring Boot的判断方法之一是检查系统中是否存在的核心类。

初始化完成后,进入run方法,run方法完成了所有Spring的整个启动过程：准备Environment——发布事件——创建上下文、bean——刷新上下文——结束，
其中穿插了很多监听器的动作，并且很多逻辑都是靠各种监听器的实现类执行的，所以在分析run方法之前，先看下各种核心监听器、接口的作用。

#### ConfigurableApplicationContext

相对于只读的ApplicationContext而言，ConfigurableApplicationContext提供了配置上下文的接口，如设置Environment、监听器、切面类、关闭上下文的钩子等，
还有刷新上下文的接口。默认是只读的接口，接口名前面加Configurable对应是一个提供可配置接口的新接口——在Spring很多配置相关的接口中都有这样的继承形式，
例如ConfigurableEnvironment和Environment、ConfigurablePropertyResolver和PropertyResolver、ConfigurableBeanFactory和BeanFactory等等。
继承的三个父类接口里，Closeable提供了关闭时资源释放的接口，Lifecycle是提供对生命周期控制的接口(start\stop)以及查询当前运行状态的接口，ApplicationContext则是配置上下文的中心配置接口，
继承了其他很多配置接口，其本身提供查询诸如id、应用程序名等上下文档案信息的只读接口，以及构建自动装配bean的工厂（注释上官方说该接口提供的工厂是用于注册上下文外部的bean的，但调试发现和在程序内@Autowired获取到的工厂是同一个对象...）。
简单写下ApplicationContext继承的父类接口。

- EnvironmentCapable 提供Environment接口。
- MessageSource 国际化资源接口。
- ApplicationEventPublisher 事件发布器。
- ResourcePatternResolver 资源加载器。
- HierarchicalBeanFactory、ListableBeanFactory 这两个都继承了bean容器的根接口BeanFactory。

