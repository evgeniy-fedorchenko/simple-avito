package com.evgeniyfedorchenko.simpleavito.dto.cachedDto;

import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.Role;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import lombok.Data;
import lombok.ToString;

/**
 * Объекты этого класса предназначены для кеширования сущности {@link AdEntity}.
 * Имеются поля связанной сущности {@link UserEntity} на один уровень связи вниз. То есть только базовые поля,
 * не представленные другими сущностями или сборными объектами
 */
@Data
public class CachedAd {

//    Поля самого объявления
    private long id;
    private String imageCombinedId;
    private Integer price;
    private String title;
    private String description;

//    Поля автора этого объявления
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
