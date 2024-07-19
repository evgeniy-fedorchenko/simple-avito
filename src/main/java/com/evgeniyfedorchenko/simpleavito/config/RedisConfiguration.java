package com.evgeniyfedorchenko.simpleavito.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@EnableCaching
public class RedisConfiguration {

    public static final String AD_CACHE = "redis_cache_for_adEntity";
    public static final String COMMENT_CACHE = "redis_cache_for_commentEntity";

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration baseCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ZERO)
                .disableCachingNullValues();

        Map<String, RedisCacheConfiguration> cacheConfigurations = Map.of(
                AD_CACHE, baseCacheConfig,
                COMMENT_CACHE, baseCacheConfig
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(baseCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
