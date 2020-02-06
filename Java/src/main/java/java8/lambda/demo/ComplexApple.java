package java8.lambda.demo;

import lombok.Data;

/**
 * @author heyou
 * @time 2020/2/6 13:35
 * @description
 */
@Data
public class ComplexApple {
    private String color;
    private Integer weight;
    private Long size;

    public ComplexApple(String color, Integer weight, Long size) {
        this.color = color;
        this.weight = weight;
        this.size = size;
    }
}
