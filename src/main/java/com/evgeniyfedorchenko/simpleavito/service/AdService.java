package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.aop.AuthChecker;
import com.evgeniyfedorchenko.simpleavito.dto.Ad;
import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateAd;
import com.evgeniyfedorchenko.simpleavito.dto.ExtendedAd;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AdService {

    Ads getAllAds();

    /**
     * Метод для создания нового объекта {@link AdEntity} из предоставленных параметров
     * и сохранение его в базу данных. Присутствует неявная проверка аутентификации
     * @param properties объект DTO, содержащий пользовательские параметры объявления
     * @param image изображение, которое должно быть прикреплено к объявлению
     * @return объект DTO, готовый для возврата из контроллера, собранный на основе свежесобранного объявления
     */
    Ad addAd(CreateOrUpdateAd properties, MultipartFile image);

    /**
     * Метод для получения подробной информации об объявлении по его {@code id}
     * @param id идентификатор объявления, для которого нужно получить информацию
     * @return непустой {@link Optional}, содержащий подробный DTO объявления, найденного по указанному {@code id}<br>
     * {@link Optional#empty()}, если по указанному {@code id} не найдено объявления
     */
    Optional<ExtendedAd> getAds(long id);

    /**
     * Метод для удаления объявления по его идентификатору. Происходит так же удаление связанного с ним изображения
     * и всех прикрепленных комментариев
     * с сервера хранения изображений. Присутствует явна проверка авторизации с помощью {@link PreAuthorize}
     * @param id идентификатор комментария, который нужно удалить
     * @return {@code true} если сущность успешно удалена<br>
     *         {@code false} в противном случае
     * @see AuthChecker#hasPermissionToEdit(long id, JpaRepository)
     */
    boolean removeAd(long id);

    /**
     * Метод для обновления существующиего объекта {@link AdEntity} из предоставленных параметров.
     * Присутствует явна проверка авторизации с помощью {@link PreAuthorize}
     * @param id идентификатор объявления, которое необходимо обновить
     * @param createOrUpdateAd объект DTO, содержащий новые параметры объявления
     * @return непустой {@link Optional}, содержащий объект DTO, готовый для возврата из контроллера,
     * собранный на основе обновленного объявления
     * {@link Optional#empty()}, если по указанному {@code id} не найдено объявления
     * @see AuthChecker#hasPermissionToEdit(long id, JpaRepository)
     */
    Optional<Ad> updateAds(long id, CreateOrUpdateAd createOrUpdateAd);

    /**
     * Метод для предоставления всех объектов {@link AdEntity} (на самом деле их DTO), связанных с авторизованным
     * в данный момент пользователем
     * @return непустой {@link Optional}, содержащий объект DTO, инкапсулирующий все объявления, принадлежащие
     * пользователю
     * Ну я хз, в каком случае тут может пустой опшнал вернуться
     * {@link Optional#empty()}, если по указанному {@code id} не найдено объявления
     */
    Optional<Ads> getAdsMe();

    /**
     * Метод для обновления изображения объявления.
     * Присутствует явна проверка авторизации с помощью {@link PreAuthorize}
     * @param id идентификатор объявления, у которого надо обновить изображение
     * @param image новое изображение, на которое должно быть заменено имеющееся
     * @return непустой {@link Optional}, содержащий массив байт нового изображения
     * {@link Optional#empty()}, если по указанному {@code id} не найдено объявления
     */
    Optional<byte[]> updateImage(long id, MultipartFile image);

    /**
     * Метод для получения изображения указанного объявления
     * @param id идентификатор объявления, у которого надо получить изображение
     * @return непустой {@link Optional}, содержащий массив байт нового изображения
     * {@link Optional#empty()}, если по указанному {@code id} не найдено объявления
     */
    Optional<byte[]> getImage(long id);
}
