package com.evgeniyfedorchenko.simpleavito.aop;

import com.evgeniyfedorchenko.simpleavito.entity.UserEntityRelated;
import com.evgeniyfedorchenko.simpleavito.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthChecker {

    private final AuthService authService;

    /**
     * Проверка прав доступа к целевому методу у авторизованного в данный момент пользователя. При вызове через
     * аннотацию {@link PreAuthorize} целевой метод допускается к выполнению, иначе весь http-запрос прерывается и
     * возвращается {@link HttpStatus#FORBIDDEN}
     * @param entityIdToEdit Id сущности с которой планируется взаимодействие в целевом методе
     * @param targetRepository целевой репозиторий, первый тип-параметр которого наследуется от
     *                         {@link UserEntityRelated}. Этот репозиторий используется для поиска
     *                         сущности, идентификатор которой указан в {@code entityIdToEdit}
     * @return {@code true}, если текущий пользователь имеет права на редактирование сущности с указанным id
     *         {@code false}, если прав недостаточно
     *
     */
    public boolean hasPermissionToEdit(long entityIdToEdit,
                                       JpaRepository<? extends UserEntityRelated, Long> targetRepository) {

                return targetRepository.findById(entityIdToEdit).filter(
                        userEntityRelated -> userEntityRelated.getAuthor().getEmail().equals(authService.getUsername())
                                || authService.isAdmin()
                ).isPresent();
    }
}
