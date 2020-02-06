package java8.lambda.demo;

/**
 * @author heyou
 * @time 2020/2/6 13:33
 * @description 多个入参的functionInterface
 */
@FunctionalInterface
public interface ThreeFunction<T,U,K,R> {
    R apply(T t,U u,K k);
}
