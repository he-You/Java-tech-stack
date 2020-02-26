package java8.stream.streamdemo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author heyou
 * @time 2020/2/11 10:33
 * @description Stream创建的几种方式:
 *                              1.Collection
 *                              2.Vaues(of方法)
 *                              3.Arrays
 *                              4.File(文件资源获取流)
 *                              5.Iterate
 */
public class CreateStream {
    /**
     * create the Stream object from Collection
     * @return listStream
     */
    private static Stream<String> createStreamFromCollection(){
        List<String> stringList =Arrays.asList("hello","heyou","world","stream");
        return  stringList.stream();
    }

    private static Stream<String> createStreamFromValues(){
        return Stream.of("hello","heyou","world","stream");
    }

    private static Stream<String> createStreamFromArrays(){
        return Arrays.stream(new String[]{"hello","heyou","world","stream"});
    }

    private static Stream<String> createStreamFromFile(){
        Path path = Paths.get("/Users/heyou/Documents/workspace/Javatechstack/Java/src/main/java/java8/stream/streamdemo/CreateStream.java");
        try {
            Stream<String> lines= Files.lines(path);
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Integer> createStreamFromIterator(){
        //limit限制迭代次数
        return Stream.iterate(0, n->n+2).limit(10);
    }

    private static Stream<Double> createStreamFromGenerate(){
        //创建10个随机数
        return Stream.generate(Math::random).limit(10);
    }
    /**
     * 自定义Supplier,生成指定内容(格式)的Stream
     */
    private static Stream<Obj> createObjStreamFromGenerate(){
        return Stream.generate(new ObjSupplier()).limit(10);
    }

    public static void main(String[] args) {
        System.out.println("create Stream from Collection");
        createStreamFromCollection().forEach(System.out::println);
        System.out.println("create Stream from values");
        createStreamFromValues().forEach(System.out::println);
        System.out.println("create Stream from Arrays");
        createStreamFromArrays().forEach(System.out::println);
        System.out.println("create Stream from file");
        createStreamFromFile().forEach(System.out::println);
        System.out.println("create Stream from Iterator");
        createStreamFromIterator().forEach(System.out::println);
        System.out.println("create Stream from Generate");
        createStreamFromGenerate().forEach(System.out::println);
        System.out.println("create Stream from ObjSupplierGenerate");
        createObjStreamFromGenerate().forEach(System.out::println);
    }
}
