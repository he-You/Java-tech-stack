### 高效的读写队列：ConcurrentLinkedQueue 类

#### 一、核心节点Node

```java
private static class Node<E> {
    volatile E item;
    volatile Node<E> next;
}
```

其中：

- item 指的是目标元素，即存入列表的元素
- next指的是当前节点 Node 的下一个元素

在对 Node 节点进行操作时，使用的是 CAS即比对赋值，这种操作是具有原子性。

```java
boolean casItem(E cmp, E val) {
    return UNSAFE.compareAndSwapObject(this, itemOffset, cmp, val);
}

void lazySetNext(Node<E> val){
    UNSAFE.putOrderedObject(this, nextOffset, val);
}

boolean casNext(Node<E> cmp, Node<E> val){
    return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
}
```

- casItem()：表示设置当前 Node 的 item 的值，需要两个参数：

  - cmp：期望值
  - val:目标值

  当当前值等于cmp期望值时，会将当前值设置为目标值。

- casNext：原理同上，只不过作用的对象是 next 字段。

#### 二、重要的字段head(头部) 和 tail（尾部）

- 对于 head 来说，它**永远不会为 null**,并且通过 head 及 succ()后继方法一定能完整遍历整个链表。
- 对于 tail ,它并不总是位于链表的尾部，因为tail 位置的更新不是及时的。

```Java
 /**
     * Inserts the specified element at the tail of this queue.
     * As the queue is unbounded, this method will never return {@code false}.
     *
     * @return {@code true} (as specified by {@link Queue#offer})
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(E e) {
        checkNotNull(e);
        final Node<E> newNode = new Node<E>(e);

        for (Node<E> t = tail, p = t;;) {
            Node<E> q = p.next;
            if (q == null) {
                // p is last node
                if (p.casNext(null, newNode)) {
                    // Successful CAS is the linearization point
                    // for e to become an element of this queue,
                    // and for newNode to become "live".
                    if (p != t) // hop two nodes at a time
                        casTail(t, newNode);  // Failure is OK.
                    return true;
                }
                // Lost CAS race to another thread; re-read next
            }
            else if (p == q)
                // We have fallen off list.  If tail is unchanged, it
                // will also be off-list, in which case we need to
                // jump to head, from which all live nodes are always
                // reachable.  Else the new tail is a better bet.
                p = (t != (t = tail)) ? t : head;
            else
                // Check for tail updates after two hops.
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }
```

注：

- 这个方法是没有进行锁操作的。线程的安全性完全由 CAS 操作和队列算法进行保障。
- 方法的核心是 for 循环，这个循环没有出口，只能在尝试成功后才能进行返回。

当第一次加入元素时，由于队列为空，p.next为 null。此时将 p 的 next 节点赋值为 newNode,即完成入队操作。此时 p==t 为 true,所以不会进行更新 tail 操作，而是一直在 for 循环内部操作，直到成功。在此过程中，tail 都不会更新位置的

当试图对第二个元素进行入队操作时，由于 t 在 head的位置上，因此 p.next 指向时间的第一个元素，因此 q!=null 表示 q 不是最好的节点。而入队需要得到最后一个元素的位置，因此开始循环查找最后一个元素：

`p = (p != t && t != (t = tail)) ? t : q;`

此时 p 实际上指向链表的第一个元素，而它的 next 为 null,故在第二个循环时，p 更新自己的 next，让它指向新加入的元素节点，如果成功在会更新 t 的所在位置，将 t 移动到链表的最后。



**哨兵(sentinel)节点**：p==q的情况。所谓哨兵节点即 next 指向自己的节点。当遇到哨兵节点，由于无法通过 next 获得后续的节点，因此很能返回 head 重新遍历。进一步找到链表的末尾。如果在执行的过程中 tail 发生改变，会尝试将 tail 作为链表的尾部避免重新查找 tail。



对于` p = (t != (t = tail)) ? t : head;`

注：

- !=并不是原子操作
- 在并发环境下 t!=t 是可能会成立的，如果两个 t 不相同表示 tail 被修改了，这时可以将新的 tail 作为链表的尾部，反之则返回 head,即从头部开始重新查找尾部。