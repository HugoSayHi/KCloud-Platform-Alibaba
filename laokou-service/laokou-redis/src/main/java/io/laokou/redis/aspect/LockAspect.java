package io.laokou.redis.aspect;

import io.laokou.redis.annotation.Lock4j;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class LockAspect {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(io.laokou.redis.annotation.Lock4j)")
    public void lockPointCut() {}

    @Around(value = "lockPointCut()")
    public void around(ProceedingJoinPoint joinPoint) {
        //获取注解
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (null == method) {
            return;
        }
        Lock4j lock4j = method.getAnnotation(Lock4j.class);
        String key = lock4j.key();
        long expire = lock4j.expire();
        long timeout = lock4j.timeout();
        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(expire, timeout, TimeUnit.SECONDS)) {
                log.info("加锁成功...");
                joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            log.error("异常信息：{}",throwable.getMessage());
        } finally {
            lock.unlock();
            log.info("解锁成功...");
        }
    }

}