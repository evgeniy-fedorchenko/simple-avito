package com.evgeniyfedorchenko.simpleavito.dto.cachedDto;

import com.evgeniyfedorchenko.simpleavito.entity.Role;
import lombok.Data;
import lombok.ToString;

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
