package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateAd;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import com.evgeniyfedorchenko.simpleavito.exception.ImageParsedException;
import com.evgeniyfedorchenko.simpleavito.mapper.AdMapper;
import com.evgeniyfedorchenko.simpleavito.repository.AdRepository;
import com.evgeniyfedorchenko.simpleavito.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final ImageService imageService;

    @Override
    @Transactional(readOnly = true)
    public Ads getAllAds() {
        return adMapper.toDtos(adRepository.findAll());
    }

    @Override
    @Transactional // TODO 18.07.2024 22:52: авторизован ли пользователь?
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image) {

        AdEntity adEntity = new AdEntity();
        CompletableFuture<String> imageIdFuture = CompletableFuture.supplyAsync(() -> imageService.saveImage(image));

        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());

        adEntity.setPrice(properties.getPrice());
        adEntity.setTitle(properties.getTitle());
        adEntity.setDescription(properties.getDescription());

        List<AdEntity> ads = userEntity.getAds();
        ads.add(adEntity);
        userEntity.setAds(ads);
        adEntity.setImageUuid(imageIdFuture.join());
        adEntity.setAuthor(userEntity);

        AdEntity saveAd = adRepository.save(adEntity);
        UserEntity savedUser = userRepository.save(userEntity);

        log.debug("Saved ad {} to user {}", saveAd, savedUser);
        return adMapper.toDto(adEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExtendedAd> getAds(long id) {
        return adRepository.findById(id).map(adMapper::toExtendedAdDto);
    }

    @Override
    @Transactional
    @PreAuthorize(value = "@authChecker.hasPermissionToEdit(#id, @adRepository)")
    public boolean removeAd(long id) {

        Optional<AdEntity> adEntityOpt = adRepository.findById(id);
        if (adEntityOpt.isEmpty()) {
            return false;
        }

        CompletableFuture.runAsync(() -> imageService.deleteImage(adEntityOpt.get().getImageUuid()));
        CompletableFuture.runAsync(() -> adRepository.deleteById(id));

        log.debug("Removing ad {}", id);
        return true;

    }

    @Override
    @Transactional
    @PreAuthorize(value = "@authChecker.hasPermissionToEdit(#id, @adRepository)")
    public Optional<Ad> updateAds(long id, CreateOrUpdateAd createOrUpdateAd) {
        Optional<AdEntity> adEntityOpt = adRepository.findById(id);

        if (adEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        AdEntity adEntity = adEntityOpt.get();
        adEntity.setTitle(createOrUpdateAd.getTitle());
        adEntity.setPrice(createOrUpdateAd.getPrice());
        adEntity.setDescription(createOrUpdateAd.getDescription());

        AdEntity savedAd = adRepository.save(adEntity);
        log.debug("Updated ad {}", savedAd);
        return Optional.of(adMapper.toDto(savedAd));

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ads> getAdsMe() {
        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());
        return Optional.of(adMapper.toDtos(userEntity.getAds()));
    }

    @Override
    @Transactional
    @PreAuthorize(value = "@authChecker.hasPermissionToEdit(#id, @adRepository)")
    public Optional<byte[]> updateImage(long id, MultipartFile image) {
        Optional<AdEntity> adEntityOpt = adRepository.findById(id);

        if (adEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        CompletableFuture.runAsync(() -> {
            AdEntity adEntity = adEntityOpt.get();
            String imageId = imageService.updateImage(adEntity.getImageUuid(), image);
            adEntity.setImageUuid(imageId);
            log.debug("Updated image of ad {}", adEntity);
            adRepository.save(adEntity);
        });

        try {
            return Optional.of(image.getBytes());
        } catch (IOException e) {
            throw new ImageParsedException("Image could not be parsed");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<byte[]> getImage(long id) {
        return adRepository.findById(id)
                .map(adEntity -> imageService.getImage(adEntity.getImageUuid()));
    }
}
