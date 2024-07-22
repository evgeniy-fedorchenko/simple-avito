package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Register;
import com.evgeniyfedorchenko.simpleavito.entity.Role;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface AuthService {

    /**
     * Метод для аутентификации и авторизации пользователей
     * по их логину (хранится в {@link UserEntity#getEmail()}) и паролю.
     * Происходит создание объекта {@link UserDetails} и передача его в {@link SecurityContext}
     * Для сравнения паролей используется {@link PasswordEncoder#matches(CharSequence raw, String encoded)}
     * @param userName логин пользователя
     * @param password пароль пользователя в незашифрованном виде
     * @return {@code true} если загрузка данных пользователя из места их хранения прошла успешно,
     * а также пароли совпали
     */
    boolean login(String userName, String password);

    /**
     * Метод для регистрации нового пользователя (создания и сохранения нового {@link UserEntity}
     * @param register объект, содержащий данные о новом пользователе
     * @return {@code true} в случае, если пользователя по такому {@code username} еще не существует
     * {@code false} в противном случае
     */
    boolean register(Register register);

    /**
     * Метод для предоставления username или значения поля {@link UserEntity#getEmail()} авторизованного в данный
     * момент пользователя
     * @return {@code username} извлеченный из {@link SecurityContextHolder}
     */
    String getUsername();

    /**
     * Метод для получения списка ролей авторизованного в данный момент пользователя
     * @return списка ролей авторизованного в данный момент пользователя
     */
    List<Role> getRoles();

    /**
     * Метод для проверки, имеет ли пользователь, авторизованный в данный момент, роль {@link Role#ADMIN}
     * @return {@code true} если пользователь, авторизованный в данный момент, роль {@link Role#ADMIN}
     * {@code false} в противном случае
     */
    boolean isAdmin();
}
