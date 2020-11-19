### Guava RateLimiter 限流及源码解析

#### 一、RateLimiter 实现原理

Guava 有两种限流模式：

- 稳定模式（SmoothBursty）:令牌生成速度恒定
- 渐进模式（SmoothWarmingUp）:令牌生成速度缓慢提升知道维持到一个稳定值

#### 二、RateLimiter 创建

通过 create 方法创建实例，实际上是调用 SmoothBursty 稳定模式创建的实例

```Java
  public static RateLimiter create(double permitsPerSecond) {
    return create(permitsPerSecond, SleepingStopwatch.createFromSystemTimer());
  }

  static RateLimiter create(double permitsPerSecond, SleepingStopwatch stopwatch) {
    RateLimiter rateLimiter = new SmoothBursty(stopwatch, 1.0 /* maxBurstSeconds */);
    rateLimiter.setRate(permitsPerSecond);
    return rateLimiter;
  }
```

SmoothBursty 中有两个构造参数：

- SleepingStopwatch:guava中的一个时钟类实例，会通过这个来计算时间及令牌
- maxBurstySeconds：未使用的令牌存活时间，默认是 1

```Java
/**
 * The work (permits) of how many seconds can be saved up if this RateLimiter is unused?
 * 在RateLimiter未使用时，最多存储几秒的令牌
 * */
 final double maxBurstSeconds;
 

/**
 * The currently stored permits.
 * 当前存储令牌数
 */
double storedPermits;

/**
 * The maximum number of stored permits.
 * 最大存储令牌数 = maxBurstSeconds * stableIntervalMicros(见下文)
 */
double maxPermits;

/**
 * The interval between two unit requests, at our stable rate. E.g., a stable rate of 5 permits
 * per second has a stable interval of 200ms.
 * 添加令牌时间间隔 = SECONDS.toMicros(1L) / permitsPerSecond；(1秒/每秒的令牌数)
 */
double stableIntervalMicros;

/**
 * The time when the next request (no matter its size) will be granted. After granting a request,
 * this is pushed further in the future. Large requests push this further than small requests.
 * 下一次请求可以获取令牌的起始时间
 * 由于RateLimiter允许预消费，上次请求预消费令牌后
 * 下次请求需要等待相应的时间到nextFreeTicketMicros时刻才可以获取令牌
 */
private long nextFreeTicketMicros = 0L; // could be either in the past or future
```

其中关键函数：

- setRate

  ```Java
  public final void setRate(double permitsPerSecond) {
    checkArgument(
        permitsPerSecond > 0.0 && !Double.isNaN(permitsPerSecond), "rate must be positive");
    synchronized (mutex()) {
      doSetRate(permitsPerSecond, stopwatch.readMicros());
    }
  ```

  该方法可以设置令牌桶每秒生成令牌的数量，内部时间通过调用 SmoothRateLimiterd的 doSetRate 来实现

- doSetRate

  ```Java
  @Override
    final void doSetRate(double permitsPerSecond, long nowMicros) {
      resync(nowMicros);
      double stableIntervalMicros = SECONDS.toMicros(1L) / permitsPerSecond;
      this.stableIntervalMicros = stableIntervalMicros;
      doSetRate(permitsPerSecond, stableIntervalMicros);
    }
  ```

  这里先通过调用`resync`生成令牌以及更新下一期令牌生成时间，然后更新stableIntervalMicros，最后又调用了`SmoothBursty`的`doSetRate`

- resync

  ```Java
  /**
   * Updates {@code storedPermits} and {@code nextFreeTicketMicros} based on the current time.
   * 基于当前时间，更新下一次请求令牌的时间，以及当前存储的令牌(可以理解为生成令牌)
   */
  void resync(long nowMicros) {
      // if nextFreeTicket is in the past, resync to now
      if (nowMicros > nextFreeTicketMicros) {
        double newPermits = (nowMicros - nextFreeTicketMicros) / coolDownIntervalMicros();
        storedPermits = min(maxPermits, storedPermits + newPermits);
        nextFreeTicketMicros = nowMicros;
      }
  }
  ```

根据令牌桶算法，桶中的令牌是持续生成存放的，有请求时需要先从桶中拿到令牌才能开始执行，谁来持续生成令牌存放呢？

一种解法是，开启一个定时任务，由定时任务持续生成令牌。这样的问题在于会极大的消耗系统资源，如，某接口需要分别对每个用户做访问频率限制，假设系统中存在6W用户，则至多需要开启6W个定时任务来维持每个桶中的令牌数，这样的开销是巨大的。

另一种解法则是延迟计算，如上`resync`函数。该函数会在每次获取令牌之前调用，其实现思路为，若当前时间晚于nextFreeTicketMicros，则计算该段时间内可以生成多少令牌，将生成的令牌加入令牌桶中并更新数据。这样一来，只需要在获取令牌时计算一次即可。

- SmoothBursty的 doSetRate

  ```Java
  @Override
  void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
    double oldMaxPermits = this.maxPermits;
    maxPermits = maxBurstSeconds * permitsPerSecond;
    if (oldMaxPermits == Double.POSITIVE_INFINITY) {
      // if we don't special-case this, we would get storedPermits == NaN, below
      // Double.POSITIVE_INFINITY 代表无穷啊
      storedPermits = maxPermits;
    } else {
      storedPermits =
          (oldMaxPermits == 0.0)
              ? 0.0 // initial state
              : storedPermits * maxPermits / oldMaxPermits;
    }
  }
  ```

  

桶中可存放的最大令牌数由maxBurstSeconds计算而来，其含义为最大存储maxBurstSeconds秒生成的令牌。
 该参数的作用在于，可以更为灵活地控制流量。如，某些接口限制为300次/20秒，某些接口限制为50次/45秒等。也就是流量不局限于qps。

#### RateLimiter 其他常用接口

```Java
@CanIgnoreReturnValue
public double acquire() {
  return acquire(1);
}

/**
* 获取令牌，返回阻塞的时间
**/
@CanIgnoreReturnValue
public double acquire(int permits) {
  long microsToWait = reserve(permits);
  stopwatch.sleepMicrosUninterruptibly(microsToWait);
  return 1.0 * microsToWait / SECONDS.toMicros(1L);
}

final long reserve(int permits) {
  checkPermits(permits);
  synchronized (mutex()) {
    return reserveAndGetWaitLength(permits, stopwatch.readMicros());
  }
}
```

acquire()主要用于获取 permits 个令牌，并计算需要等待的时间，进而挂起等待，并将该值返回，主要通过 reserve 返回需要等待的时间，reserve 中通过 reserveAndGetWaitLength 获取等待时间。

```Java
/**
 * Reserves next ticket and returns the wait time that the caller must wait for.
 *
 * @return the required wait time, never negative
 */
final long reserveAndGetWaitLength(int permits, long nowMicros) {
  long momentAvailable = reserveEarliestAvailable(permits, nowMicros);
  return max(momentAvailable - nowMicros, 0);
}
```

最后调用了 reserveEarliestAvailable

```Java
@Override
final long reserveEarliestAvailable(int requiredPermits, long nowMicros) {
  resync(nowMicros);
  long returnValue = nextFreeTicketMicros;
  double storedPermitsToSpend = min(requiredPermits, this.storedPermits);
  double freshPermits = requiredPermits - storedPermitsToSpend;
  long waitMicros =
      storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend)
          + (long) (freshPermits * stableIntervalMicros);

  this.nextFreeTicketMicros = LongMath.saturatedAdd(nextFreeTicketMicros, waitMicros);
  this.storedPermits -= storedPermitsToSpend;
  return returnValue;
}
```

首先通过resync生成令牌以及同步nextFreeTicketMicros时间戳，freshPermits从令牌桶中获取令牌后还需要的令牌数量，通过storedPermitsToWaitTime计算出获取freshPermits还需要等待的时间，在稳定模式中，这里就是(long) (freshPermits * stableIntervalMicros) ，然后更新nextFreeTicketMicros以及storedPermits，这次获取令牌需要的等待到的时间点， reserveAndGetWaitLength返回需要等待的时间间隔。

从`reserveEarliestAvailable`可以看出RateLimiter的预消费原理，以及获取令牌的等待时间时间原理（可以解释示例结果），当获取令牌不足时，并没有等待到令牌全部生成，而是**更新了下次获取令牌时的nextFreeTicketMicros，从而影响的是下次获取令牌的等待时间。**

 `reserve`这里返回等待时间后，`acquire`通过调用`stopwatch.sleepMicrosUninterruptibly(microsToWait);`进行sleep操作，这里不同于Thread.sleep(), 这个函数的sleep是uninterruptibly的，内部实现：

```Java
public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
    //sleep 阻塞线程 内部通过Thread.sleep()
  boolean interrupted = false;
  try {
    long remainingNanos = unit.toNanos(sleepFor);
    long end = System.nanoTime() + remainingNanos;
    while (true) {
      try {
        // TimeUnit.sleep() treats negative timeouts just like zero.
        NANOSECONDS.sleep(remainingNanos);
        return;
      } catch (InterruptedException e) {
        interrupted = true;
        remainingNanos = end - System.nanoTime();
        //如果被interrupt可以继续，更新sleep时间，循环继续sleep
      }
    }
  } finally {
    if (interrupted) {
      Thread.currentThread().interrupt();
      //如果被打断过，sleep过后再真正中断线程
    }
  }
}
```

sleep 之后，`acquire`返回 sleep的时间，阻塞结束，获得到令牌。

`tryAcquire`函数可以尝试在timeout时间内获取令牌，如果可以则挂起等待相应时间并返回true，否则立即返回false
 `canAcquire`用于判断timeout时间内是否可以获取令牌，通过判断**当前时间+超时时间**是否大于nextFreeTicketMicros 来决定是否能够拿到足够的令牌数，如果可以获取到，则过程同acquire，线程sleep等待，如果通过`canAcquire`在此超时时间内不能获取到令牌，则可以快速返回，不需要等待timeout后才知道能否获取到令牌。

```Java
public boolean tryAcquire(int permits) {
  return tryAcquire(permits, 0, MICROSECONDS);
}

public boolean tryAcquire() {
  return tryAcquire(1, 0, MICROSECONDS);
}

public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
  long timeoutMicros = max(unit.toMicros(timeout), 0);
  checkPermits(permits);
  long microsToWait;
  synchronized (mutex()) {
    long nowMicros = stopwatch.readMicros();
    if (!canAcquire(nowMicros, timeoutMicros)) {
      return false;
    } else {
      microsToWait = reserveAndGetWaitLength(permits, nowMicros);
    }
  }
  stopwatch.sleepMicrosUninterruptibly(microsToWait);
  return true;
}

private boolean canAcquire(long nowMicros, long timeoutMicros) {
  return queryEarliestAvailable(nowMicros) - timeoutMicros <= nowMicros;
}

@Override
final long queryEarliestAvailable(long nowMicros) {
  return nextFreeTicketMicros;
}
```

