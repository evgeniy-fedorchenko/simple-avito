package com.evgeniyfedorchenko.simpleavito.config;

import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.service.AdServiceImpl;
import com.evgeniyfedorchenko.simpleavito.service.CommentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Класс конфигурации для объектов типа {@code Class<? extends UserEntityRelated>}. Необходимо поместить эти
 * объекты в Spring Context, чтобы они были доступны в выражениях  SpEL, используемых в аргументах аннотации
 * {@link PreAuthorize} на метод в сервисных классах, например:<br>
 * <ul>
 *     <li>{@link AdServiceImpl#updateAds(long, CreateOrUpdateAd)}</li>
 *     <li>{@link CommentServiceImpl#deleteComment(long, long)}</li>
 * </ul>
 */
@Configuration
public class UserEntityRelatedConfiguration {

    @Bean
    public Class<AdEntity> adEntityClass() {
        return AdEntity.class;
    }

    @Bean
    public Class<CommentEntity> commentEntityClass() {
        return CommentEntity.class;
    }

}
