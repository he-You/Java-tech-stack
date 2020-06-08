### AQS 介绍 

一、基础介绍

1.AQS(AbstractQueuedSynchronizer),是构建锁和同步器的框架。ReentrantLock，Semaphore，其他的诸如 ReentrantReadWriteLock都是基于 AQS 实现的

2.原理：

- 概述：如果请求的共享资源空闲，则将当前请求的线程设置为有效的工作线程，并将资源设置成锁定状态。如果资源被占用，AQS 会利用 **CLH 队列锁**实现阻塞以及等待唤醒的锁分配机制，将等待的线程加入队列中，等待资源空闲时系统的锁分配。

  - 其中：CLH(Craig,Landin,and Hagersten)队列是一个虚拟的双向队列（虚拟的双向队列即不存在队列实例，仅存在结点之间的关联关系）。AQS 是将每条请求共享资源的线程封装成一个 CLH 锁队列的一个结点（Node）来实现锁的分配。

- 同步：AQS使用的一个 int 变量表示同步状态，通过内置的 FIFO队列获取线程队列，并且使用 CAS 操作进行状态的修改（即 protected 修饰的 getState()/setState()/compareAndSetState()）

  ```java
  private volatile int state;//共享变量，使用volatile修饰保证线程可见性
  
  //返回同步状态的当前值
  protected final int getState() {
          return state;
  }
   // 设置同步状态的值
  protected final void setState(int newState) {
          state = newState;
  }
  //原子操作（CAS操作）将同步状态值设置为给定值update如果当前同步状态的值等于expect（期望值）
  protected final boolean compareAndSetState(int expect, int update) {
          return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
  }
  ```

  

3.AQS 对资源的共享方式

- 独占 Exclusive：只有一个线程能执行，如 ReentrantLock。又可分为公平锁和非公平锁,ReentrantLock 同时支持两种锁(默认公平锁)

  - 公平锁：先到先得，根据队列的排队顺序依次拿到锁
  - 非公平锁：当前线程请求获得锁时，需要通过 两次CAS操作争抢锁，如果没有抢到锁，放入队列中等待唤醒。

  ```java
  /** Synchronizer providing all implementation mechanics */
  private final Sync sync;
  public ReentrantLock() {
      // 默认非公平锁
      sync = new NonfairSync();
  }
  public ReentrantLock(boolean fair) {
      sync = fair ? new FairSync() : new NonfairSync();
  }
  ```

  ReentrantLock之公平锁 lock方法

  ```java
  static final class FairSync extends Sync {
      final void lock() {
          acquire(1);
      }
      // AbstractQueuedSynchronizer.acquire(int arg)
      public final void acquire(int arg) {
          if (!tryAcquire(arg) &&
              acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
              selfInterrupt();
      }
      protected final boolean tryAcquire(int acquires) {
          final Thread current = Thread.currentThread();
          int c = getState();
          if (c == 0) {
              // 1. 和非公平锁相比，这里多了一个判断：是否有线程在等待
              if (!hasQueuedPredecessors() &&
                  compareAndSetState(0, acquires)) {
                  setExclusiveOwnerThread(current);
                  return true;
              }
          }
          else if (current == getExclusiveOwnerThread()) {
              int nextc = c + acquires;
              if (nextc < 0)
                  throw new Error("Maximum lock count exceeded");
              setState(nextc);
              return true;
          }
          return false;
      }
  }
  ```

  ReentrantLock之非公平锁 lock方法

  ```java
  static final class NonfairSync extends Sync {
      final void lock() {
          // 2. 和公平锁相比，这里会直接先进行一次CAS，成功就返回了
          if (compareAndSetState(0, 1))
              setExclusiveOwnerThread(Thread.currentThread());
          else
              acquire(1);
      }
      // AbstractQueuedSynchronizer.acquire(int arg)
      public final void acquire(int arg) {
          if (!tryAcquire(arg) &&
              acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
              selfInterrupt();
      }
      protected final boolean tryAcquire(int acquires) {
          return nonfairTryAcquire(acquires);
      }
  }
  /**
   * Performs non-fair tryLock.  tryAcquire is implemented in
   * subclasses, but both need nonfair try for trylock method.
   */
  final boolean nonfairTryAcquire(int acquires) {
      final Thread current = Thread.currentThread();
      int c = getState();
      if (c == 0) {
          // 这里没有对阻塞队列进行判断
          if (compareAndSetState(0, acquires)) {
              setExclusiveOwnerThread(current);
              return true;
          }
      }
      else if (current == getExclusiveOwnerThread()) {
          int nextc = c + acquires;
          if (nextc < 0) // overflow
              throw new Error("Maximum lock count exceeded");
          setState(nextc);
          return true;
      }
      return false;
  }
  ```

  注：公平锁和非公平锁之间存在两点不同：

  - 非公平锁在调用 lock 后，首先就会调用 CAS 进行一次抢锁，如果这个时候恰巧锁没有被占用，那么直接就获取到锁返回了。
  - 非公平锁在 CAS 失败后，和公平锁一样都会进入到 tryAcquire 方法，在 tryAcquire 方法中，如果发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面。

  公平锁和非公平锁就这两点区别，**如果这两次 CAS 都不成功，那么后面非公平锁和公平锁是一样的，都要进入到阻塞队列等待唤醒。**

  相对来说，**非公平锁会有更好的性能**，因为它的吞吐量比较大。当然，非公平锁让获取锁的时间变得更加不确定，可能会导致在阻塞队列中的线程长期处于饥饿状态。

- 共享（Share）

  多个线程可同时执行，如 Semaphore/CountDownLatch。Semaphore、CountDownLatCh、 CyclicBarrier、ReadWriteLock 我们都会在后面讲到。

  ReentrantReadWriteLock 可以看成是组合式，因为 ReentrantReadWriteLock 也就是读写锁允许多个线程同时对某一资源进行读。

  不同的自定义同步器争用共享资源的方式也不同。自定义同步器在实现时只需要实现共享资源 state 的获取与释放方式即可，至于具体线程等待队列的维护（如获取资源失败入队/唤醒出队等），AQS 已经在上层已经帮我们实现好了。