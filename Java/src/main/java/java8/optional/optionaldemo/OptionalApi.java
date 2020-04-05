package java8.optional.optionaldemo;

import java.util.Optional;

/**
 * @author heyou
 * @time 2020/1/25 18:48
 */
public class OptionalApi {
    public static void main(String[] args) {
        User user = new User();
        Optional<User> userOptional = Optional.<User>empty();
        Optional<User> optionalUser = Optional.of(new User());

        System.out.println(optionalUser.get());

        Optional<Object> objectOptional = Optional.ofNullable(null);

        System.out.println(objectOptional);
        objectOptional.orElseGet(User::new);
        objectOptional.orElse(new User());
        /*objectOptional.orElseThrow(RuntimeException::new);
        objectOptional.orElseThrow(()->new RuntimeException("Not Fount Reference"));*/
        // 是否有值
        System.out.println(objectOptional.isPresent());
        Optional<User> optional = Optional.of(user);
        //optional.orElse(new User("heyou",1,24,180.0));

        optional.ifPresent((u)-> System.out.println("user"));
        optional.ifPresent(System.out::println);
        Optional<String> optional1 = optional.map((a) -> "key" + a);
        System.out.println(optional1.get());

        getUserName(null);
        getUserNameByOptional(null);
    }

    private static String getUserName(User user){
        if(null == user){
            return "unknown";
        }
        return user.getName();
    }

    private static String getUserNameByOptional(User user){
        return Optional.ofNullable(user).map(User::getName).orElse("unknown");
    }
}
