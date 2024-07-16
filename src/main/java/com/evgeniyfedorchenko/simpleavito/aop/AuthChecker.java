package com.evgeniyfedorchenko.simpleavito.aop;

import com.evgeniyfedorchenko.simpleavito.entity.UserEntityRelated;
import com.evgeniyfedorchenko.simpleavito.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthChecker {

    /**
     * Так как этот класс проверяет права доступа для объектов разного типа - репозитории тоже нужны разные.
     * В данном случае это список всех объявленных репозиториев, которые параметризованы {@link UserEntityRelated}
     * или потомками этого класса
     */
    private final List<JpaRepository<UserEntityRelated, Long>> jpaRepositories;
    private final AuthService authService;

    /**
     * Проверка прав доступа к целевому методу у авторизованного в данный момент пользователя. При вызове через
     * аннотацию {@link PreAuthorize} целевой метод допускается к выполнению, иначе весь http-запрос прерывается и
     * возвращается {@link HttpStatus#FORBIDDEN}
     * @param entityIdToEdit Id сущности с которой планируется взаимодействие в целевом методе
     * @param concreteRelatedEntityClass Класс, который наследуется от {@link UserEntityRelated}. Этот класс
     *                                   используется для поиска соответствующего репозитория JPA и должен совпадать
     *                                   с классом  сущности, идентификатор которой указан в {@code entityIdToEdit}
     * @return {@code true}, если текущий пользователь имеет права на редактирование сущности с указанным id
     *         {@code false}, если прав недостаточно
     * @throws IllegalArgumentException Если {@code concreteRelatedEntityClass} не поддерживается
     *
     */
    public boolean hasPermissionToEdit(long entityIdToEdit,
                                       Class<? extends UserEntityRelated> concreteRelatedEntityClass) {

        for (JpaRepository<UserEntityRelated, Long> jpaRepository : jpaRepositories) {
            if (jpaRepository.getClass().getGenericInterfaces()[0] instanceof ParameterizedType parameterizedType
                    && parameterizedType.getActualTypeArguments()[0].equals(concreteRelatedEntityClass)
            ) {

                return jpaRepository.findById(entityIdToEdit).filter(
                        userEntityRelated -> userEntityRelated.getAuthor().getEmail().equals(authService.getUsername())
                                || authService.isAdmin()
                ).isPresent();
            }
        }
        throw new IllegalArgumentException("Logic error. Please check parameters. %s is not supported"
                .formatted(concreteRelatedEntityClass));
    }
}
