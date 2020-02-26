package sourcecode.cycledepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heyou
 * @date 2020/2/19 21:11
 */
@Component
public class Y {
    @Autowired
    X x;
    public Y(){
        System.out.println("Y create");
    }
}
