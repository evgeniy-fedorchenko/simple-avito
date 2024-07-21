package com.evgeniyfedorchenko.simpleavito.aop;

import com.evgeniyfedorchenko.simpleavito.config.CacheConfiguration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CachingAspect {

    private final RedisCacheManager cacheManager;

    protected abstract void save(JoinPoint joinPoint, Object result);

    protected abstract void deleteById(JoinPoint joinPoint);

    protected abstract Object findById(ProceedingJoinPoint joinPoint) throws Throwable;

    protected Cache getCache(JoinPoint joinPoint) {
        Class<?> invokingClass = joinPoint.getSignature().getDeclaringType();
        String cacheName = CacheConfiguration.cacheRepoMapping.get(invokingClass);
        return cacheManager.getCache(cacheName);
    }
}
