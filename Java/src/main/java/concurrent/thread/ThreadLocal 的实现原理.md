### ThreadLocal 的实现原理

1. set方法

```Java
   /**
     * Sets the current concurrent.thread's copy of this concurrent.thread-local variable
     * to the specified value.  Most subclasses will have no need to
     * override this method, relying solely on the {@link #initialValue}
     * method to set the values of concurrent.thread-locals.
     *
     * @param value the value to be stored in the current concurrent.thread's copy of
     *        this concurrent.thread-local.
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
```

将此线程局部变量的当前线程副本设置为指定值。大多数子类不需要重写这个方法，只需要依赖{@link #initialValue}方法来设置线程局部变量的值。

在调用 set 方法时，首先获取当前线程对象，通过 getMap 方法拿到线程的 ThreadLocalMap（这个对象并不是严格意义上的 Map，是定义在 Thread内部的成员）并存入值。这个 map 的 key 的当前对象，value 是我们需要的值。由此可以看出存入时相当于保存了当前线程的所有“局部变量”。



2. get方法

```Java
   /**
     * Returns the value in the current concurrent.thread's copy of this
     * concurrent.thread-local variable.  If the variable has no value for the
     * current concurrent.thread, it is first initialized to the value returned
     * by an invocation of the {@link #initialValue} method.
     *
     * @return the current concurrent.thread's value of this concurrent.thread-local
     */
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
```

返回此线程局部变量的当前线程副本中的值。如果该变量没有当前线程的值，则首先将其初始化为调用{@link #initialValue}方法返回的值。



注：

- ThreadLocalMap 是Thread 的内部成员，也就是说**如果当前线程不退出，对象的引用或者是副本值会一直存在**，因此在实际开发场景中我们需要在不使用 ThreadLocal 时进行“副本数据”的清理，其中就包括对 ThreadLocalMap 的清理。
- 使用线程池时就意味着当前线程可能不会及时退出，当 ThreadLocal存入大对象而又不及时回收时就可能使系统出现内存泄漏。
- Thread.remove()在实际开发过程中就显得十分重要。

```Java
   /**
     * This method is called by the system to give a Thread
     * a chance to clean up before it actually exits.
     */
    private void exit() {
        if (group != null) {
            group.threadTerminated(this);
            group = null;
        }
        /* Aggressively null out all reference fields: see bug 4006245 */
        target = null;
        /* Speed the release of some of these resources */
        threadLocals = null;
        inheritableThreadLocals = null;
        inheritedAccessControlContext = null;
        blocker = null;
        uncaughtExceptionHandler = null;
    }
```

ThreadLocalMap 的实现其实是使用了弱引用，虚拟机在进行垃圾回收的时候，如果发现了弱引用就会立即回收。ThreadLocalMap 内部由一系列 Entry构成，每个 Entry 都是 WeakReference<ThreadLocal>。

```Java
static class Entry extends WeakRefrence<ThreadLocal> {
    Object value;
    Entry(ThreadLocal k, Object v)  {
        super(k);
        value = v;
    }
}
```

其中：key 就ThreadLocal 实例，作为弱引用使用，super(k)调用的是 WeadReference的构造函数。当 ThreadLocal 的外部强引用被回收时，ThreadLocal 中的 key就会变成 null。当系统进行 ThreadLocalMap 清理时，就会将这些垃圾数据回收。

