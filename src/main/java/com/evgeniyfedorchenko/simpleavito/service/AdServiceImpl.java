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

@Slf4j
@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    @Transactional(readOnly = true)
    public Ads getAllAds() {
        return adMapper.toDtos(adRepository.findAll());
    }

    @Override
    @Transactional
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image) {

        AdEntity adEntity = new AdEntity();

        try {
            adEntity.setImage(image.getBytes());
        } catch (IOException _) {
            throw new ImageParsedException("Image could not be parsed");
        }

        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());

        adEntity.setPrice(properties.getPrice());
        adEntity.setTitle(properties.getTitle());
        adEntity.setDescription(properties.getDescription());

        List<AdEntity> ads = userEntity.getAds();
        ads.add(adEntity);
        userEntity.setAds(ads);

        adEntity.setAuthor(userEntity);
        UserEntity savedUser = userRepository.save(userEntity);

        log.debug("Saved ad {} to user {}", adEntity, savedUser);
        return adMapper.toDto(adEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExtendedAd> getAds(long id) {
        return adRepository.findById(id).map(adMapper::toExtendedAdDto);
    }

    @Override
    @Transactional
    @PreAuthorize(value = "authChecker.hasPermissionToEdit(id, @adEntityClass)")
    public boolean removeAd(long id) {

        if (!adRepository.existsById(id)) {
            return false;
        }

        adRepository.deleteById(id);
        log.debug("Removed ad {}", id);
        return true;

    }

    @Override
    @PreAuthorize(value = "authChecker.hasPermissionToEdit(id, @adEntityClass)")
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
    @PreAuthorize(value = "authChecker.hasPermissionToEdit(id, @adEntityClass)")
    public Optional<byte[]> updateImage(long id, MultipartFile image) {
        Optional<AdEntity> adEntityOpt = adRepository.findById(id);

        if (adEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        AdEntity adEntity = adEntityOpt.get();
        try {
            adEntity.setImage(image.getBytes());
            AdEntity savedAd = adRepository.save(adEntity);
            log.debug("Updated image of ad {}", adEntity);
            return Optional.of(savedAd.getImage());

        } catch (IOException _) {
            throw new ImageParsedException("Image could not be parsed");
        }
    }
}
