package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.exception.ImageParsedException;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageKit imageKit;
    public static final String SEPARATOR = "<SEP>";


    @Override
    public @Nullable String saveImage(MultipartFile image) {
        try {

            Result result = imageKit.upload(new FileCreateRequest(image.getBytes(), String.valueOf(UUID.randomUUID())));
            return result.getFileId() + SEPARATOR + result.getUrl();

        } catch (IOException _) {   // Ошибка извлечения байтов из MultipartFile
            throw new ImageParsedException("Cannot purse the image " + image.getOriginalFilename());
        } catch (Exception ex) {   // Ошибка отправки изображения на сервер
            log.error("Cannot upload image to external server. Ex:{}", ex.getMessage());
            return null;
        }
    }

    @Override
    public byte[] getImage(String combinedImageId) {
        String url = combinedImageId.split(SEPARATOR)[1];
        try (InputStream in = URI.create(url).toURL().openStream()) {

            return in.readAllBytes();

        } catch (IOException ex) {
            log.error("Cannot download image from external server. Ex:{}", ex.getMessage());
            return new byte[0];
        }
    }

    @Override
    public void deleteImage(String combinedImageId) {
        try {

            String deleteId = combinedImageId.split(SEPARATOR)[0];
            imageKit.deleteFile(deleteId);

        } catch (Exception ex) {
            log.error("Cannot delete image from external server. Ex:{}", ex.getMessage());
        }
    }

    @Override
    public @Nullable String updateImage(String oldCombinedImageId, MultipartFile newImage) {
        CompletableFuture.runAsync(() -> this.deleteImage(oldCombinedImageId));
        return this.saveImage(newImage);
    }
}
