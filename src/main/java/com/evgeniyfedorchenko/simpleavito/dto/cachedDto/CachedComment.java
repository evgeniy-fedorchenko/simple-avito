package com.evgeniyfedorchenko.simpleavito.dto.cachedDto;

import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.entity.Role;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

/**
 * Объекты этого класса предназначены для кеширования сущности {@link CommentEntity}.
 * Имеются поля связанных сущностей {@link UserEntity} и {@link AdEntity} на один уровень связи вниз.
 * То есть только базовые поля, не представленные другими сущностями или сборными объектами
 */
@Data
public class CachedComment {

//    Поля самого комментария
    private long id;
    private Instant createdAt;
    private String text;

    //    Поля объявления этого комментария
    private long adId;
    private String adImageCombinedId;
    private Integer adPrice;
    private String adTitle;
    private String adDescription;

//     Поля автора объявления
    private long authorId;
    private String authorEmail;
    private String authorFirstName;
    private String authorLastName;
    private String authorPhone;
    private Role authorRole;
    @ToString.Exclude
    private String authorPassword;
    private String authorImageCombinedId;

}
