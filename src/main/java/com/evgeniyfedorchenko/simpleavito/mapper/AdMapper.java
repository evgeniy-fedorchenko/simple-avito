package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.AdController;
import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class AdMapper {

    @Value("${server.port}")
    private int port;

    public Ad toDto(AdEntity adEntity) {
        Ad ad = new Ad();

        ad.setAuthor(adEntity.getAuthor().getId());
        ad.setImage(adEntity.hasImage() ? generateImageUrl(adEntity.getId()) : "no image");
        ad.setPk(adEntity.getId());
        ad.setPrice(adEntity.getPrice());
        ad.setTitle(adEntity.getTitle());

        return ad;
    }

    public Ads toDto(List<AdEntity> adEntities) {
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
        extendedAd.setDescription(adEntity.hasDescription() ? adEntity.getDescription() : "no description");
        extendedAd.setEmail(author.getEmail());
        extendedAd.setImage(adEntity.hasImage() ? generateImageUrl(adEntity.getId()) : "no image");
        extendedAd.setPhone(author.getPhone());
        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setTitle(adEntity.getTitle());

        return extendedAd;
    }

    private String generateImageUrl(int id) {

        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path(AdController.BASE_AD_URI)
                .pathSegment(String.valueOf(id), AdController.IMAGE_PATH_SEGMENT)

                .build()
                .toUri()
                .toString();
    }

}
