package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

public interface AdService {

    void getAllAds();

    Ad addAd(CreateOrUpdateAd properties, MultipartFile image);

    ExtendedAd getAds(int id);

    void removeAd(int id);

    Ad updateAds(int id, CreateOrUpdateAd createOrUpdateAd);

    Ads getAdsMe();

    byte[] updateImage(int id, MultipartFile image);
}
