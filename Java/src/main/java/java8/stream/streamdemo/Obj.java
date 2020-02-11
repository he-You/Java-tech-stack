package java8.stream.streamdemo;

import lombok.Data;

/**
 * @author heyou
 * @time 2020/2/11 11:38
 * @description
 */
@Data
public class Obj {
    private int id;
    private String name;

    public Obj(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
