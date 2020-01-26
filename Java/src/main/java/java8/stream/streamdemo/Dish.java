package java8.stream.streamdemo;

import lombok.Data;

import java.lang.reflect.Type;

/**
 * @author heyou
 * @time 2020/1/26 22:54
 */
@Data
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;

    public Dish(String name, boolean vegetarian, int calories) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
    }
}
