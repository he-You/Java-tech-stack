package java8.optional.optionaldemo;

/**
 * @author heyou
 * @time 2020/1/25 21:56
 */

public class User {
    private String name;
    private Integer sex;
    private Integer age;
    private Double height;

    public User() {
    }

    public User(String name, Integer sex, Integer age, Double height) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
