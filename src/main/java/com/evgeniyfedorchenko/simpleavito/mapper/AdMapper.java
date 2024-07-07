package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.AdController;
import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class AdMapper {

    public Ad toDto(AdEntity adEntity) {
        Ad ad = new Ad();

        ad.setAuthor(adEntity.getAuthor().getId());
        ad.setImage(adEntity.hasImage() ? generateImageUrl(adEntity.getId()) : null);
        ad.setPk(adEntity.getId());
        ad.setPrice(adEntity.getPrice());
        ad.setTitle(adEntity.getTitle());

        return ad;
    }

    public Ads toDtos(List<AdEntity> adEntities) {
        Ads ads = new Ads();

        ads.setCount(adEntities.size());
        ads.setResults(adEntities.stream().map(this::toDto).toList());

        return ads;
    }

    public ExtendedAd toExtendedAdDto(AdEntity adEntity) {
        ExtendedAd extendedAd = new ExtendedAd();
        UserEntity author = adEntity.getAuthor();

        extendedAd.setPk(adEntity.getId());
        extendedAd.setAuthorFirstName(author.getFirstName());
        extendedAd.setAuthorLastName(author.getLastName());
        extendedAd.setDescription(adEntity.getDescription());
        extendedAd.setEmail(author.getEmail());
        extendedAd.setImage(adEntity.hasImage() ? generateImageUrl(adEntity.getId()) : null);
        extendedAd.setPhone(author.getPhone());
        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setTitle(adEntity.getTitle());

        return extendedAd;
    }

    private String generateImageUrl(long id) {

        return UriComponentsBuilder.newInstance()
                .path(AdController.BASE_AD_URI)
                .pathSegment(String.valueOf(id), AdController.IMAGE_PATH_SEGMENT)

                .build()
                .toUriString();
    }

}
