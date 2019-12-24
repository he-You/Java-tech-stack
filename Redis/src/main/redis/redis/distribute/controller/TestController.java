package redis.distribute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.distribute.annotation.RedisLock;

/**
 * 简要说明:
 *
 * @author heyou
 * @date 2019-12-24 14:19
 */
@RestController
public class TestController {
    @RedisLock(key = "redis_lock")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
