package java8.optional.optionaldemo;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Collections;
import java.util.Optional;

/**
 * @author heyou
 * @time 2020/1/25 18:48
 */
public class OptionalApi {
    public static void main(String[] args) {
        User user = new User();
        Optional<User> optional = Optional.of(user);
        //optional.orElse(new User("heyou",1,24,180.0));

        optional.ifPresent((u)-> System.out.println("user"));
        optional.ifPresent(System.out::println);
        Optional<String> optional1 = optional.map((a) -> "key" + a);
        System.out.println(optional1.get());
    }
}
