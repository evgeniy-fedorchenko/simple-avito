package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.AdController;
import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import jakarta.annotation.Nullable;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "image", expression = "java(generateImageUrl(adEntity))")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    ExtendedAd toExtendedDto(AdEntity adEntity);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "author.id", target = "author")
    @Mapping(target = "image", expression = "java(generateImageUrl(adEntity))")
    Ad toDto(AdEntity adEntity);

    List<Ad> toDtoList(List<AdEntity> adEntities);

    default Ads toDtos(List<AdEntity> adEntities) {
        Ads ads = new Ads();
        ads.setCount(adEntities.size());
        ads.setResults(adEntities.stream().map(this::toDto).toList());
        return ads;
    }

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.email", target = "authorEmail")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    @Mapping(source = "author.phone", target = "authorPhone")
    @Mapping(source = "author.role", target = "authorRole")
    @Mapping(source = "author.password", target = "authorPassword")
    @Mapping(source = "author.imageCombinedId", target = "authorImageCombinedId")
    CachedAd adEntityToCachedAd(AdEntity adEntity);

    @InheritInverseConfiguration
    @Mapping(target = "comments", ignore = true)
    AdEntity cachedAdToAdEntity(CachedAd cachedAd);

    default @Nullable String generateImageUrl(AdEntity adEntity) {
        if (!adEntity.hasImage()) {
            return null;
        }
        return UriComponentsBuilder.newInstance()
                .path(AdController.BASE_AD_URI)
                .pathSegment(String.valueOf(adEntity.getId()), AdController.IMAGE_PATH_SEGMENT)

                .build()
                .toUriString();
    }
}
