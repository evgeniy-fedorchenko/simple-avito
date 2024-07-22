package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Класс, представляющий собой сущность, которой может владеть пользователь (объект {@link UserEntity})
 * Наследование строго контролируется с помощью модификаторов {@code sealed} и {@code permits}
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@MappedSuperclass
public abstract sealed class UserEntityRelated permits AdEntity, CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity author;

}
