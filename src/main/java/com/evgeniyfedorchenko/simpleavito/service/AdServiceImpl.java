package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateAd;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import com.evgeniyfedorchenko.simpleavito.mapper.AdMapper;
import com.evgeniyfedorchenko.simpleavito.repository.AdRepository;
import com.evgeniyfedorchenko.simpleavito.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image, String username) {

        AdEntity adEntity = new AdEntity();

        try {
            adEntity.setImage(image.getBytes());
            adEntity.setMediaType(image.getContentType());
        } catch (IOException _) {
            throw new java.awt.image.ImagingOpException("Image could not be parsed");
        }

        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());

        adEntity.setPrice(properties.getPrice());
        adEntity.setTitle(properties.getTitle());
        adEntity.setDescription(properties.getDescription());
        userEntity.addAd(adEntity);

        UserEntity savedUser = userRepository.save(userEntity);
        AdEntity savedAd = adRepository.save(adEntity);

        log.debug("Saved ad {} to user {}", savedAd, savedUser);
        return adMapper.toDto(savedAd);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExtendedAd> getAds(long id) {
        return adRepository.findById(id).map(adMapper::toExtendedAdDto);
    }

    @Override
    @Transactional
    public boolean removeAd(long id) {
        boolean exist = adRepository.existsById(id);
        if (exist) {
            adRepository.deleteById(id);
            log.debug("Removed ad {}", id);
        }
        return exist;
    }

    @Override
    public Optional<Ad> updateAds(long id, CreateOrUpdateAd createOrUpdateAd) {
        Optional<AdEntity> adEntityOpt = adRepository.findById(id);

        if (adEntityOpt.isPresent()) {
            AdEntity adEntity = adEntityOpt.get();
            adEntity.setTitle(createOrUpdateAd.getTitle());
            adEntity.setPrice(createOrUpdateAd.getPrice());
            adEntity.setDescription(createOrUpdateAd.getDescription());

            AdEntity savedAd = adRepository.save(adEntity);
            log.debug("Updated ad {}", savedAd);
            return Optional.of(adMapper.toDto(savedAd));
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ads> getAdsMe(String name) {
        UserEntity userEntity = userRepository.findByEmail(authService.getUsername());
        return Optional.of(adMapper.toDtos(userEntity.getAds()));
    }

    @Override
    @Transactional
    public Optional<Pair<byte[], MediaType>> updateImage(long id, MultipartFile image) {
        Optional<AdEntity> adEntityOpt = adRepository.findById(id);

        if (adEntityOpt.isPresent()) {
            try {
                AdEntity adEntity = adEntityOpt.get();
                adEntity.setImage(image.getBytes());
                adEntity.setMediaType(image.getContentType());

                AdEntity savedAd = adRepository.save(adEntity);
                log.debug("Updated image of ad {}", adEntity);
                return Optional.of(Pair.of(savedAd.getImage(), MediaType.parseMediaType(savedAd.getMediaType())));

            } catch (IOException _) {
                throw new java.awt.image.ImagingOpException("Image could not be parsed");
            }
        }
        return Optional.empty();
    }
}
