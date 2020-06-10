如何判断 两个List是否相同

1.List中的需要比对的对象（属性）需要重写 hashcode 和 equals 方法

2.List 转 Set 利用 Set 元素不能重复的特性判断 List 中的元素是否相同

```Java
public static void main(String[] args) {
        List<Test> oldList = UserController.getTestOldList();
        Set<Test> oldSet = new HashSet<>(oldList);
        List<Test> newList = UserController.getTestNewList();
        Set<Test> newSet = new HashSet<>(newList);
        for(Test t : newSet){
            boolean add = oldSet.add(t);
            if(add){
                System.out.println("元素不相同");
            }
        }
    }


    private static List<Test> getTestNewList(){
        Test testNew1 = new Test();
        testNew1.setId("123456");
        testNew1.setValue("heyou");
        List<A> aOldList = Arrays.asList(new A("1","value1"),new A("2","value2"),new A("3","value3"));
        testNew1.setContentList(aOldList);

        Test testNew2 = new Test();
        testNew2.setId("1234567");
        testNew2.setValue("heyou2");
        testNew2.setName("Nick");
        List<A> aOldList2 = Arrays.asList(new A("1","value1"),new A("key2","value2"),new A("3","value3"));
        testNew2.setContentList(aOldList2);

        Test testNew3 = new Test();
        testNew3.setId("12345678");
        testNew3.setValue("heyou3");
        List<A> aOldList3 = Arrays.asList(new A("1","value1"),new A("2","value2"),new A("key3","value3"));
        testNew3.setContentList(aOldList3);

        Test testNew4 = new Test();
        testNew4.setId("123456789");
        testNew4.setValue("heyou4");
        List<A> aOldList4 = Arrays.asList(new A("key1","value1"),new A("2","value2"),new A("3","value3"));
        testNew4.setContentList(aOldList4);
        List<Test> testNewList = Arrays.asList(testNew4,testNew2,testNew1,testNew3);
        return testNewList;
    }

    private static List<Test> getTestOldList(){
        Test testOld1 = new Test();
        testOld1.setId("123456");
        testOld1.setValue("heyou");
        List<A> aOldList = Arrays.asList(new A("1","value1"),new A("2","value2"),new A("3","value3"));
        testOld1.setContentList(aOldList);

        Test testOld2 = new Test();
        testOld2.setId("1234567");
        testOld2.setValue("heyou2");
        List<A> aOldList2 = Arrays.asList(new A("1","value1"),new A("key2","value2"),new A("3","value3"));
        testOld2.setContentList(aOldList2);

        Test testOld3 = new Test();
        testOld3.setId("12345678");
        testOld3.setValue("heyou3");
//        List<A> aOldList3 = Arrays.asList(new A("1","value1"),new A("2","value2"),new A("key3","value3"));
//        testOld1.setContentList(aOldList3);

        Test testOld4 = new Test();
        testOld4.setId("123456789");
        testOld4.setValue("heyou4");
//        List<A> aOldList4 = Arrays.asList(new A("key1","value1"),new A("2","value2"),new A("3","value3"));
//        testOld1.setContentList(aOldList4);
        List<Test> testOldList = Arrays.asList(testOld1,testOld2,testOld3,testOld4);
        return testOldList;
    }
    
    public static <E>boolean isListEqual(List<E> list1, List<E> list2) {
        // 两个list引用相同（包括两者都为空指针的情况）
//        if (list1 == list2) {
//            return true;
//        }
//
//        // 两个list都为空（包括空指针、元素个数为0）
//        if ((list1 == null && list2 != null && list2.size() == 0)
//                || (list2 == null && list1 != null && list1.size() == 0)) {
//            return true;
//        }

        // 两个list元素个数不相同
        if (list1.size() != list2.size()) {
            return false;
        }

        // 两个list元素个数已经相同，再比较两者内容
        // 采用这种可以忽略list中的元素的顺序
        // 涉及到对象的比较是否相同时，确保实现了equals()方法
        if (!list1.containsAll(list2)) {
            return false;
        }

        return true;
    }
```

测试实体类

```java
public class Test {
    private String id;

    private String value;

    private String name;

    private List<A> contentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;  
    }

    public List<A> getContentList() {
        return contentList;
    }

    public void setContentList(List<A> contentList) {
        this.contentList = contentList;
    }
// 必须重写 equals 和 hashcode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Test test = (Test) o;
        return Objects.equals(id, test.id) &&
                Objects.equals(value, test.value) &&
                Objects.equals(contentList, test.contentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, contentList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

