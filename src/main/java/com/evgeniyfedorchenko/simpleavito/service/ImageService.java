package com.evgeniyfedorchenko.simpleavito.service;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    /**
     * Метод для сохранения изображения на внешнем сервере
     * @param image объект изображения, который необходимо сохранить
     * @return сборный идентификатор изображения, представляющий собой fileId,
     *         специфичный для сервера изображений и прямой ссылки для скачивания байтов
     *         изображения, разделенных специальным разделителем {@link ImageServiceImpl#SEPARATOR}<br>
     *         <b>Если не удалось загрузить изображение, будет возвращен {@code null}</b>
     */
    @Nullable String saveImage(MultipartFile image);

    /**
     * Метод для скачивания байтов изображения
     * @param combinedImageId сборный идентификатор изображения, хранящийся в сущности, к которой привязано изображение
     * @return скачанное изображение в виде массива байт. При ошибке скачивания возвращается пустой массив
     */
    byte[] getImage(String combinedImageId);

    /**
     * Метод для удаления изображения с удаленного сервера. Должен быть вызван при удалении сущности. Любые исключения
     * вплоть до уровня {@link java.lang.Exception} будут подавлены и залогированы
     * @param combinedImageId сборный идентификатор изображения, хранящийся в сущности, к которой привязано изображение
     */
    void deleteImage(String combinedImageId);

    /**
     * Метод обновления изображения, связанного с целевым объектом.
     * Происходит удаление старого изображения и сохранение нового
     * @param oldCombinedImageId сборный идентификатор старого, более не актуального изображения
     * @param newImage объект нового изображения, который необходимо сохранить
     * @return сборный идентификатор изображения, представляющий собой fileId, специфичный
     *         для сервера изображений и прямой ссылки для скачивания байтов изображения,
     *         разделенных специальным разделителем {@link ImageServiceImpl#SEPARATOR}<br>
     *         <b>Если не удалось загрузить изображение, будет возвращен {@code null}</b>
     */
    @Nullable String updateImage(String oldCombinedImageId, MultipartFile newImage);
}
