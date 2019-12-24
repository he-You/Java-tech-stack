package redis.distribute.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.distribute.config.RedisProperties;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简要说明:
 *
 * @author heyou
 * @date 2019-12-24 14:07
 */
@Component
public class JedisUtil {
    @Resource
    private RedisProperties redisProperties;
    private Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    private Map<String, JedisPool> map = new ConcurrentHashMap<>();

    private  JedisPool getPool() {
        String key = redisProperties.getHost() + ":" + redisProperties.getHost();
        JedisPool pool;
        if (!map.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(redisProperties.getMax_idle());
            config.setMaxWaitMillis(redisProperties.getMax_wait());
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            pool = new JedisPool(config,redisProperties.getHost(),redisProperties.getPort(),redisProperties.getTimeout(),redisProperties.getPassword());
            map.put(key, pool);
        } else {
            pool = map.get(key);
        }
        return pool;
    }

    public Jedis getJedis() {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                jedis = getPool().getResource();
                count++;
            } catch (Exception e) {
                logger.error("get jedis failed ", e);
                if (jedis != null) {
                    jedis.close();
                }
            }
        } while (jedis == null && count < redisProperties.getRetry_num());
        return jedis;
    }
}
