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


    /**
     * Совет, применяемый к методам jpa репозиториев {@code S save(S entity)}.
     * Применяемая стратегия кеширования аналогична методу {@link Cache#put(Object key, Object value)}
     * Реальный метод должен быть вызван всегда, полученный результат (если он и есть и валиден) должен быть закеширован
     * @param joinPoint представление точки среза (перехвата) программы, где должен быть применен совет
     * @param result результат, возвращенный из метода (если он был выполнен)
     */
    protected abstract void save(JoinPoint joinPoint, Object result);

    /**
     * Совет, применяемый к методам jpa репозиториев {@code void deleteById(ID id)}.
     * Применяемая стратегия кеширования аналогична методу {@link Cache#evict(Object key)}
     * Реальный метод должен быть вызван всегда, полученный результат (если он и есть и валиден)
     * должен быть удален из кеша
     * @param joinPoint представление точки среза (перехвата) программы, где должен быть применен совет
     */
    protected abstract void deleteById(JoinPoint joinPoint);

    /**
     * Совет, применяемый к методам jpa репозиториев {@code Optional<T> findById(ID id)}.
     * Применяемая стратегия кеширования аналогична методу {@link Cache#get(Object key)}
     * Перед вызовом реального метода должна быть осуществлен поиск значения в кеше по указанному ключу.
     * В случае нахождения должен Реальный метод не должен быть вызван. В случае ненахождения значения по ключу,
     * реальный метод вызывается, а результат кешируется (если он и есть и валиден)
     * @param joinPoint представление точки среза (перехвата) программы, где должен быть применен совет
     */
    protected abstract Object findById(ProceedingJoinPoint joinPoint) throws Throwable;

    protected Cache getCache(JoinPoint joinPoint) {
        Class<?> invokingClass = joinPoint.getSignature().getDeclaringType();
        String cacheName = CacheConfiguration.cacheRepoMapping.get(invokingClass);
        return cacheManager.getCache(cacheName);
    }
}
