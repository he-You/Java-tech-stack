### Spring 事务之回滚机制

#### 一、简介

1.事务回滚的机制是为了防止程序运行过程中出现异常，导致数据库产生脏数据。即当程序出现异常时，数据库的状态回滚到异常之前的状态；

2.异常的架构



![img](https://img2018.cnblogs.com/i-beta/1593395/202001/1593395-20200102143950291-1396164540.png)

3.checked 和 unchecked 异常

Spring使用声明式事务处理，**默认情况下，如果被注解的数据库操作方法中发生了unchecked异常**，所有的数据库操作将rollback；**如果发生的异常是checked异常，默认情况下数据库操作还是会提交的。**

**checked异常：** 

表示无效，不是程序中可以预测的。比如无效的用户输入，文件不存在，网络或者数据库链接错误。这些都是外在的原因，都不是程序内部可以控制的。 

必须在代码中显式地处理。比如try-catch块处理，或者给所在的方法加上throws说明，将异常抛到调用栈的上一层。 

继承自java.lang.Exception（java.lang.RuntimeException除外）。

**unchecked异常：** 

表示错误，程序的逻辑错误。是RuntimeException的子类，比如IllegalArgumentException, NullPointerException和IllegalStateException。 

不需要在代码中显式地捕获unchecked异常做处理。 

继承自java.lang.RuntimeException（而java.lang.RuntimeException继承自java.lang.Exception）。

#### 二、@Transactional的使用

1. 相关概念

- Spring 的@Transactional 注解是基于动态代理的机制，提供一种事务管理方式；

- 一般使用是通过如下代码对方法或接口或类注释：

  **@Transactional(propagation=Propagation.NOT_SUPPORTED)**

  其中：

  Propagation支持7种不同的传播机制：

  **REQUIRED：**如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务。

  **SUPPORTS：** 如果存在一个事务，支持当前事务。如果没有事务，则非事务的执行。但是对于事务同步的事务管理器，PROPAGATION_SUPPORTS与不使用事务有少许不同。

  **NOT_SUPPORTED：**总是非事务地执行，并挂起任何存在的事务。

  **REQUIRESNEW：**总是开启一个新的事务。如果一个事务已经存在，则将这个存在的事务挂起。

  **MANDATORY：**如果已经存在一个事务，支持当前事务。如果没有一个活动的事务，则抛出异常。

  **NEVER：**总是非事务地执行，如果存在一个活动事务，则抛出异常

  **NESTED：**如果一个活动的事务存在，则运行在一个嵌套的事务中。如果没有活动事务，则按REQUIRED属性执行。

- @Transactional 注解只能应用到 public 可见度的方法上。 如果你在 protected、private 或者 package-visible 的方法上使用 @Transactional 注解，它也不会报错， 但是这个被注解的方法将不会展示已配置的事务设置。

2.回滚机制

- 判断是否能够回滚的逻辑如下：
   (1) 根据@Transactional注解中rollbackFor、rollbackForClassName、noRollbackForClassName配置的值，找到最符合ex的异常类型，如果符合的异常类型不是NoRollbackRuleAttribute，则可以执行回滚。
   (2) 如果@Transactional没有配置，则默认使用RuntimeException和Error异常。

- 事务处理：

```java
txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
```

交给事务管理器回滚事务。

```java
private void processRollback(DefaultTransactionStatus status) {
    try {
        try {
            triggerBeforeCompletion(status);
            //如果有安全点，回滚至安全点
            if (status.hasSavepoint()) {
                if (status.isDebug()) {
                    logger.debug("Rolling back transaction to savepoint");
                }
                status.rollbackToHeldSavepoint();
            }
            //如果是新事务，回滚事务
            else if (status.isNewTransaction()) {
                if (status.isDebug()) {
                    logger.debug("Initiating transaction rollback");
                }
                doRollback(status);
            }
            //如果有事务但不是新事务，则把标记事务状态，等事务链执行完毕后统一回滚
            else if (status.hasTransaction()) {
                if (status.isLocalRollbackOnly() || isGlobalRollbackOnParticipationFailure()) {
                    if (status.isDebug()) {
                        logger.debug("Participating transaction failed - marking existing transaction as rollback-only");
                    }
                    doSetRollbackOnly(status);
                }
                else {
                    if (status.isDebug()) {
                        logger.debug("Participating transaction failed - letting transaction originator decide on rollback");
                    }
                }
            }
            else {
                logger.debug("Should roll back transaction but cannot - no transaction available");
            }
        }
        catch (RuntimeException ex) {
            triggerAfterCompletion(status, TransactionSynchronization.STATUS_UNKNOWN);
            throw ex;
        }
        catch (Error err) {
            triggerAfterCompletion(status, TransactionSynchronization.STATUS_UNKNOWN);
            throw err;
        }
        triggerAfterCompletion(status, TransactionSynchronization.STATUS_ROLLED_BACK);
    }
    finally {
        //清空记录的资源并将挂起的资源恢复
        cleanupAfterCompletion(status);
    }
}
```

事务处理的逻辑总结起来如下：

1. 如果存在安全点，则回滚事务至安全点，这个主要是处理嵌套事务，回滚安全点的操作还是交给了数据库处理.

   ```java
   public void rollbackToHeldSavepoint() throws TransactionException {
       if (!hasSavepoint()) {
           throw new TransactionUsageException(
                   "Cannot roll back to savepoint - no savepoint associated with current transaction");
       }
       getSavepointManager().rollbackToSavepoint(getSavepoint());
       getSavepointManager().releaseSavepoint(getSavepoint());
       setSavepoint(null);
   }
   ```

   

2. 当前事务是一个新事务时，那么直接回滚，使用的是DataSourceTransactionManager事务管理器，所以调用DataSourceTransactionManager中的doRollback方法,直接调用数据库连接的回滚方法。

   ```java
   @Override
   protected void doRollback(DefaultTransactionStatus status) {
       DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
       Connection con = txObject.getConnectionHolder().getConnection();
       if (status.isDebug()) {
           logger.debug("Rolling back JDBC transaction on Connection [" + con + "]");
       }
       try {
           con.rollback();
       }
       catch (SQLException ex) {
           throw new TransactionSystemException("Could not roll back JDBC transaction", ex);
       }
   }
   ```

   

3. 当前存在事务，但又不是一个新的事务，只把事务的状态标记为read-only，等到事务链执行完毕后，统一回滚,调用DataSourceTransactionManager的doSetRollbackOnly

   ```java
   @Override
   protected void doSetRollbackOnly(DefaultTransactionStatus status) {
       DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
       if (status.isDebug()) {
           logger.debug("Setting JDBC transaction [" + txObject.getConnectionHolder().getConnection() +
                   "] rollback-only");
       }
       txObject.setRollbackOnly();
   }
   ```

   

4. 清空记录的资源并将挂起的资源恢复

   ```java
   private void cleanupAfterCompletion(DefaultTransactionStatus status) {
       //设置完成状态，避免重复调用
       status.setCompleted();
       //如果是新的同步状态，则需要将绑定到当前线程的事务信息清理，传播行为中挂起事务的都会清理
       if (status.isNewSynchronization()) {
           TransactionSynchronizationManager.clear();
       }
       //如果是新事务，清理数据库连接
       if (status.isNewTransaction()) {
           doCleanupAfterCompletion(status.getTransaction());
       }
       //将挂起的事务恢复
       if (status.getSuspendedResources() != null) {
           if (status.isDebug()) {
               logger.debug("Resuming suspended transaction after completion of inner transaction");
           }
           resume(status.getTransaction(), (SuspendedResourcesHolder) status.getSuspendedResources());
       }
   }
   ```

   