package sourcecode.cycledepends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heyou
 * @date 2020/2/19 21:09
 */
@Component
public class X {
    @Autowired
    Y y;
    public X(){
        System.out.println("X create");
    }
}
