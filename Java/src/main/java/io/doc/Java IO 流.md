### Java IO 流

#### 一、BIO

BIO :block IO 即阻塞式 IO 流

- 按照流的流向分，可以分为输入流和输出流；

- 按照操作单元划分，可以划分为字节流和字符流；

  字节流主要用来处理字节或二进制对象，字符流用来处理字符文本或字符串

  使用InputStreamReader可以将输入字节流转化为输入字符流:

  ```
  Reader reader  =  new InputStreamReader(inputStream);
  ```

  使用OutputStreamWriter可以将输出字节流转化为输出字符流

  ```
  Writer writer = new OutputStreamWriter(outputStream)
  ```

- 按照流的角色划分为节点流和处理流。

![](../img/bio.png)

**1） 输入字节流InputStream**：

- **ByteArrayInputStream、StringBufferInputStream、FileInputStream** 是三种基本的介质流，它们分别从Byte 数组、StringBuffer、和本地文件中读取数据。
- **PipedInputStream** 是从与其它线程共用的管道中读取数据。PipedInputStream的一个实例要和PipedOutputStream的一个实例共同使用，共同完成管道的读取写入操作。主要用于线程操作。
- **DataInputStream**： 将基础数据类型读取出来
- **ObjectInputStream** 和所有 **FilterInputStream** 的子类都是装饰流（装饰器模式的主角）。

**2）输出字节流OutputStream：**

- **ByteArrayOutputStream**、**FileOutputStream**： 是两种基本的介质流，它们分别向- Byte 数组、和本地文件中写入数据。
- **PipedOutputStream** 是向与其它线程共用的管道中写入数据。
- **DataOutputStream** 将基础数据类型写入到文件中
- **ObjectOutputStream** 和所有 **FilterOutputStream** 的子类都是装饰流。

**3）字符输入流Reader：：**

- **FileReader、CharReader、StringReader** 是三种基本的介质流，它们分在本地文件、Char 数组、String中读取数据。
- **PipedReader**：是从与其它线程共用的管道中读取数据
- **BufferedReader** ：加缓冲功能，避免频繁读写硬盘
- **InputStreamReader**： 是一个连接字节流和字符流的桥梁，它将字节流转变为字符流。

**4）字符输出流Writer：**

- **StringWriter**:向String 中写入数据。
- **CharArrayWriter**：实现一个可用作字符输入流的字符缓冲区
- **PipedWriter**:是向与其它线程共用的管道中写入数据
- **BufferedWriter** ： 增加缓冲功能，避免频繁读写硬盘。
- **PrintWriter** 和**PrintStream** 将对象的格式表示打印到文本输出流。 极其类似，功能和使用也非常相似
- **OutputStreamWriter**： 是OutputStream 到Writer 转换的桥梁，它的子类FileWriter 其实就是一个实现此功能的具体类。功能和使用和OutputStream 极其类似。

**以读写文件为例**

**（1）从数据源中读取数据**

```
输入字节流：InputStream
```

```
public static void main(String[] args) throws Exception{
       File file = new File("D:/a.txt");
       InputStream inputStream = new FileInputStream(file);
       byte[] bytes = new byte[(int) file.length()];
       inputStream.read(bytes);
       System.out.println(new String(bytes));
       inputStream.close();
}

输入字符流：Reader
public static void main(String[] args) throws Exception{
       File file = new File("D:/a.txt");
       Reader reader = new FileReader(file);
       char[] bytes = new char[(int) file.length()];
       reader.read(bytes);
       System.out.println(new String(bytes));
       reader.close();
}
```

**（2）输出到目标媒介**

```
输出字节流：OutputStream
public static void main(String[] args) throws Exception{
       String var = "hai this is a test";
       File file = new File("D:/b.txt");
       OutputStream outputStream = new FileOutputStream(file);
       outputStream.write(var.getBytes());
       outputStream.close();
}
输出字符流：Writer
public static void main(String[] args) throws Exception{
       String var = "hai this is a test";
       File file = new File("D:/b.txt");
       Writer writer = new FileWriter(file);
       writer.write(var);
       writer.close();
}
```

**（3）BufferedInputStream**

在使用InputStream的时候，都是一个字节一个字节的读或写，而BufferedInputStream为输入字节流提供了缓冲区，读数据的时候会一次读取一块数据放到缓冲区里，当缓冲区里的数据被读完之后，输入流会再次填充数据缓冲区，直到输入流被读完，有了缓冲区就能够提高很多io速度

使用方式将输入流包装到BufferedInputStream中

```
/**  
* inputStream 输入流  
* 1024 内部缓冲区大小为1024byte  
*/ 
BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,1024);
```

**（4）BufferedOutputStream**

BufferedOutputStream可以为输出字节流提供缓冲区，作用与BufferedInputStream类似

使用方式将输出流包装到BufferedOutputStream中

```
/**  
* outputStream 输出流  
* 1024 内部缓冲区大小为1024byte  
*/ 
BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream,1024);
```

字节流提供了带缓冲区的，那字符流肯定也提供了BufferedReader和BufferedWriter

**（5）BufferedReader**

为输入字符流提供缓冲区，使用方式如下

BufferedReader bufferedReader = new BufferedReader(reader,1024);

**（6）BufferedWriter**

为输出字符流提供缓冲区，使用方式如下

```
BufferedWriter bufferedWriter = new BufferedWriter(writer,1024);
```

