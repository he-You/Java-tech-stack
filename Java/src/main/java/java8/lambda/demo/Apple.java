package java8.lambda.demo;

import lombok.Data;

/**
 * @author heyou
 * @time 2020/1/29 10:47
 * @description 实体类
 */
@Data
public class Apple {
    private String color;
    private long weight;

    public Apple(String color, long weight) {
        this.color = color;
        this.weight = weight;
    }
}
