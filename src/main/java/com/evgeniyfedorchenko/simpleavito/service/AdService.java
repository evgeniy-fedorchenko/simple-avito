package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import org.springframework.web.multipart.MultipartFile;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateAd;

import java.util.Optional;

public interface AdService {

    Ads getAllAds();

    Ad addAd(CreateOrUpdateAd properties, MultipartFile image);

    Optional<ExtendedAd> getAds(long id);

    boolean removeAd(long id);

    Optional<Ad> updateAds(long id, CreateOrUpdateAd createOrUpdateAd);

    Optional<Ads> getAdsMe();

    Optional<byte[]> updateImage(long id, MultipartFile image);
}
