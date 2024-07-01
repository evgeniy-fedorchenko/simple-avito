package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import org.springframework.web.multipart.MultipartFile;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateAd;

public interface AdService {

    Ads getAllAds();

    Ad addAd(CreateOrUpdateAd properties, MultipartFile image);

    ExtendedAd getAds(int id);

    void removeAd(int id);

    Ad updateAds(int id, CreateOrUpdateAd createOrUpdateAd);

    Ads getAdsMe();

    byte[] updateImage(int id, MultipartFile image);
}
