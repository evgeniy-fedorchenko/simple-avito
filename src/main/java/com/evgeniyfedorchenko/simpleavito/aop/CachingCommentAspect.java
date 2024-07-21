package com.evgeniyfedorchenko.simpleavito.aop;

import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedComment;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.mapper.CommentMapper;
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
public class CachingCommentAspect extends CachingAspect {

    private final CommentMapper commentMapper;

    private CachingCommentAspect(RedisCacheManager cacheManager, CommentMapper commentMapper) {
        super(cacheManager);
        this.commentMapper = commentMapper;
    }
    @Pointcut("execution(* com.evgeniyfedorchenko.simpleavito.repository.CommentRepository.save(..))")
    public void savePointcut() {
    }

    @Pointcut("execution(* com.evgeniyfedorchenko.simpleavito.repository.CommentRepository.findById(..))")
    public void findByIdPointcut() {
    }

    @Pointcut("execution(* com.evgeniyfedorchenko.simpleavito.repository.CommentRepository.deleteById(..))")
    public void deleteByIdPointcut() {
    }

    @Override
    @AfterReturning(
            pointcut = "savePointcut()",
            returning = "result"
    )
    public void save(JoinPoint joinPoint, Object result) {
        Cache cache = getCache(joinPoint);

        if (result instanceof CommentEntity commentEntity) {
            CachedComment cachedcomment = commentMapper.commentEntityToCachedComment(commentEntity);
            cache.put(commentEntity.getId(), cachedcomment);
        }
    }

    @Override
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

        CachedComment value = cache.get(key, CachedComment.class);
        if (value != null) {
            CommentEntity result = commentMapper.cachedCommentToCommentEntity(value);
            return Optional.of(result);
        }

        Object result = joinPoint.proceed();

        if (result instanceof Optional<?> optional && optional.isPresent()) {
            CachedComment cacheComment = commentMapper.commentEntityToCachedComment(((CommentEntity) optional.get()));
            cache.put(key, cacheComment);
        }
        return result;

    }
}

