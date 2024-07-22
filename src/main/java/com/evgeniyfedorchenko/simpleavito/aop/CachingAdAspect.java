package com.evgeniyfedorchenko.simpleavito.aop;

import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.mapper.AdMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class CachingAdAspect extends CachingAspect {

    private final AdMapper adMapper;

    private CachingAdAspect(RedisCacheManager cacheManager, AdMapper adMapper) {
        super(cacheManager);
        this.adMapper = adMapper;
    }

    @Pointcut("execution(* com.evgeniyfedorchenko.simpleavito.repository.AdRepository.save(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* com.evgeniyfedorchenko.simpleavito.repository.AdRepository.findById(..))")
    public void findByIdPointcut() {
    }

    @Pointcut("execution(* com.evgeniyfedorchenko.simpleavito.repository.AdRepository.deleteById(..))")
    public void deleteByIdPointcut() {
    }

    @Override
    @AfterReturning(
            pointcut = "savePointcut()",
            returning = "result"
    )
    public void save(JoinPoint joinPoint, Object result) {
        Cache cache = getCache(joinPoint);

        if (result instanceof AdEntity adEntity) {
            CachedAd cachedAd = adMapper.adEntityToCachedAd(adEntity);
            cache.put(adEntity.getId(), cachedAd);
        }
    }

    @AfterReturning(pointcut = "deleteByIdPointcut()")
    public void deleteById(JoinPoint joinPoint) {
        Cache cache = getCache(joinPoint);
        Object key = joinPoint.getArgs()[0];
        cache.evict(key);
    }

    @Override
    @Around("findByIdPointcut()")
    public Object findById(ProceedingJoinPoint joinPoint) throws Throwable {

        Object key = joinPoint.getArgs()[0];
        Cache cache = getCache(joinPoint);

        CachedAd value = cache.get(key, CachedAd.class);
        if (value != null) {
            AdEntity result = adMapper.cachedAdToAdEntity(value);
            return Optional.of(result);
        }

        Object result = joinPoint.proceed();

        if (result instanceof Optional<?> optional && optional.isPresent()) {
            CachedAd cachedAd = adMapper.adEntityToCachedAd(((AdEntity) optional.get()));
            cache.put(key, cachedAd);
        }
        return result;
    }
}
