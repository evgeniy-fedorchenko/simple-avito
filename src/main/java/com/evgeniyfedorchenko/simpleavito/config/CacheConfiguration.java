package com.evgeniyfedorchenko.simpleavito.config;

import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedAd;
import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedComment;
import com.evgeniyfedorchenko.simpleavito.repository.AdRepository;
import com.evgeniyfedorchenko.simpleavito.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfiguration {

    private final ObjectMapper objectMapper;

    public static final String AD_CACHE = "redis_cache_for_adEntity";
    public static final String COMMENT_CACHE = "redis_cache_for_commentEntity";

    public static final Map<Class<? extends JpaRepository<?, Long>>, String> cacheRepoMapping = Map.of(
            AdRepository.class, AD_CACHE,
            CommentRepository.class, COMMENT_CACHE
    );

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration baseConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
                .entryTtl(Duration.ZERO)
                .disableCachingNullValues();

        Jackson2JsonRedisSerializer<CachedAd> adSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, CachedAd.class);
        Jackson2JsonRedisSerializer<CachedComment> commentSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, CachedComment.class);

        RedisCacheConfiguration adConfig = baseConfig.serializeValuesWith(fromSerializer(adSerializer));
        RedisCacheConfiguration commentConfig = baseConfig.serializeValuesWith(fromSerializer(commentSerializer));

        Map<String, RedisCacheConfiguration> cacheConfigurations = Map.of(
                AD_CACHE, adConfig,
                COMMENT_CACHE, commentConfig
        );

        return RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
