package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public Ads getAllAds() {
        return new Ads();
    }

    @Override
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image) {
        return new Ad();
    }

    @Override
    public ExtendedAd getAds(int id) {
        return new ExtendedAd();
    }

    @Override
    public void removeAd(int id) {
    }

    @Override
    public Ad updateAds(int id, CreateOrUpdateAd createOrUpdateAd) {
        return new Ad();
    }

    @Override
    public Ads getAdsMe() {
        return new Ads();
    }

    @Override
    public byte[] updateImage(int id, MultipartFile image) {
        return new byte[0];
    }
}
