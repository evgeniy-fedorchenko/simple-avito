package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.NewPassword;
import com.evgeniyfedorchenko.simpleavito.dto.UpdateUser;
import com.evgeniyfedorchenko.simpleavito.dto.User;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {

    /**
     * Метод для задания нового пароль авторизованному пользователю
     * @param newPassword DTO содержащий старый пароль для проверки подлинности
     *                    пользователя и новый пароль для установки
     * @return {@code true} если{@link NewPassword#getCurrentPassword()} сопал с
     * паролем пользователя, авторизованного в данный момент. Сравнение паролей
     * происходит с помощью {@link PasswordEncoder#matches(CharSequence rawPasswordFromThisDto, String encodedPasswordFromContext)}
     * {@code false} в противном случае
     */
    boolean setPassword(NewPassword newPassword);

    /**
     * Метод для возврата пользователя, авторизованного в данный момент.
     * Поиск осуществляется в месте хранения юзером
     * @return объект DTO, представляющий пользователя, авторизованного в данный момент
     */
    User getUser();

    /**
     * Метод для обновления авторизованного пользователя, (его {@link CommentEntity}) из предоставленных
     * параметров. Целевой пользователь будет взят из контекста,
     * @param updateUser объект DTO, содержащий новые параметры объекта {@link UserEntity}
     * @return объект DTO, собранный из обновленного {@code UserEntity}
     */
    UpdateUser updateUser(UpdateUser updateUser);

    /**
     * Метод для обновления аватара авторизованного в данный момент пользователя.
     * @param image новый аватар, на который должен быть заменен имеющийся
     * @return {@code true} если пользователь был успешно загружен из контекста и его
     * аватар успешно обновлен
     * {@code false} в противном случае
     */
    boolean updateUserImage(MultipartFile image);

    /**
     * Метод для получения аватара указанного пользователя
     * @param id идентификатор сущности пользователя, у которого надо получить аватар
     * @return непустой {@link Optional}, содержащий массив байт нового изображения
     * {@link Optional#empty()}, если по указанному {@code id} не найдено сущности пользователя
     */
    Optional<byte[]> getImage(long id);
}
