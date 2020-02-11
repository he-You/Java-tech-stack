package java8.stream.streamdemo;

import java.util.Random;
import java.util.function.Supplier;

/**
 * @author heyou
 * @time 2020/2/11 11:39
 * @description
 */
public class ObjSupplier implements Supplier<Obj> {
    private  int index = 0;

    private Random random = new Random(System.currentTimeMillis());

    @Override
    public Obj get() {
        index = random.nextInt(100);
        return new Obj(index,"Name->"+index);
    }
}
