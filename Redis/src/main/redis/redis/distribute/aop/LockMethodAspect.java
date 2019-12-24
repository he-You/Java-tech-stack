package redis.distribute.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.distribute.annotation.RedisLock;
import redis.distribute.utils.JedisUtil;
import redis.distribute.utils.RedisLockHelper;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 简要说明:AOP拦截器
 *
 * @author heyou
 * @date 2019-12-24 14:04
 */
@Aspect
@Component
public class LockMethodAspect {
    @Resource
    private RedisLockHelper redisLockHelper;
    @Resource
    private JedisUtil jedisUtil;
    private Logger logger = LoggerFactory.getLogger(LockMethodAspect.class);

    @Around("@annotation(redis.distribute.annotation.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        Jedis jedis = jedisUtil.getJedis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String value = UUID.randomUUID().toString();
        String key = redisLock.key();
        try {
            final boolean islock = redisLockHelper.lock(jedis,key, value, redisLock.expire(), redisLock.timeUnit());
            logger.info("isLock : {}",islock);
            if (!islock) {
                logger.error("获取锁失败");
                throw new RuntimeException("获取锁失败");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        }  finally {
            logger.info("释放锁");
            redisLockHelper.unlock(jedis,key, value);
            jedis.close();
        }
    }
}
