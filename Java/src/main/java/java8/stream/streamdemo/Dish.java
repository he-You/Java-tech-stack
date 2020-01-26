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
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public enum Type{
        MEAT,FISH,OTHER
    }
}
