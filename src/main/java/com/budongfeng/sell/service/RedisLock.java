package com.budongfeng.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 *  @Author 田培融  canghaihongxin@163.com
 *  @Company 不动峰 www.budongfeng.com
 *  @Date 2017/12/28/028 17:46
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key  productId
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {

        // 代码一:   如果只有代码块一的话，当加完锁之后， 后续的代码出现问题了。 就抛出异常了 没有办法执行解锁了。 就会造成死锁
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {  // setIfAbsent 是redis中的setnx命令，来使用!setnx来实现redis锁
            return true;
        }

        // 代码二:
        //currentValue=A   这两个线程的value都是B  其中一个线程拿到锁
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {   // 存储进去的时候 小于当前系统时间
            //获取上一个锁的时间，并把当前时间+超时时间 设置进去
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);    //两个线程同时进入这个方法， 第一个线程进来之后获取到了值，并把自己的值添加进去
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {  //
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("【redis分布式锁】解锁异常, {}", e);
        }
    }

}
