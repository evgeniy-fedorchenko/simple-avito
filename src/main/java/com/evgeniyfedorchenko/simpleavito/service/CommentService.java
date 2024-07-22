package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.aop.AuthChecker;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateComment;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface CommentService {

    Optional<Comments> getComments(long adId);

    /**
     * Метод для создания нового объекта {@link CommentEntity} из предоставленных параметров
     * и сохранение его в базу данных. Присутствует неявная проверка аутентификации
     * @param createOrUpdateComment объект DTO, содержащий пользовательские параметры объявления
     * @param adId идентификатор объявления, к которому должен быть прикреплен свежесобранный комментарий
     * @return непустой {@link Optional}, содержащий объект DTO, готовый для возврата из контроллера,
     * собранный на основе свежесобранного комментария
     * {@link Optional#empty()}, если по указанному {@code id} не найдено объявления для привязки
     */
    Optional<Comment> addComment(long adId, CreateOrUpdateComment createOrUpdateComment);

    /**
     * Метод для удаления комментария по его идентификатору ({@code commentId}.
     * Присутствует проверка на правильно указанных идентификатор связанного объявления ({@code adId}).
     * Присутствует явна проверка авторизации с помощью {@link PreAuthorize}
     * @param commentId идентификатор комментария, который нужно удалить
     * @param adId идентификатор объявление, к которому привязан комментарий
     * @return {@code true} если сущность успешно удалена<br>
     *         {@code false} в противном случае
     * @see AuthChecker#hasPermissionToEdit(long id, JpaRepository)
     */
    boolean deleteComment(long adId, long commentId);

    /**
     * Метод для обновления существующиего объекта {@link CommentEntity} из предоставленных параметров
     * по указанному {@code commentId}. Присутствует проверка идентификатора привязанного объявления,
     * а так же явна проверка авторизации с помощью {@link PreAuthorize}
     * @param adId идентификатор объявления, к которому привязан комментарий
     * @param commentId идентификатор комментария, который необходимо обновить
     * @param comment объект DTO, содержащий новые параметры объявления
     * @return непустой {@link Optional}, содержащий объект DTO, готовый для возврата из контроллера,
     * собранный на основе обновленного комментария
     * {@link Optional#empty()}, если по указанному {@code id} не найдено комментария или если комментарий
     * не привязан к объявлению, найденному по идентификатору {@code фвШв}
     * @see AuthChecker#hasPermissionToEdit(long id, JpaRepository)
     */
    Optional<Comment> updateComment(long adId, long commentId, CreateOrUpdateComment comment);
}
