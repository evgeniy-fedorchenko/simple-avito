package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    /*
     * Комментарий по поводу типа данных java.sql.Timestamp
     *
     * Мне нравится пользоваться аннотацией org.hibernate.annotations.CreationTimestamp - она автоматически ставит
     * текущее время в это поле, когда сущность впервые сохраняется в бд и не надо вводить его руками.
     * Но @CreationTimestamp предназначена для работы с типами, представляющими дату и время в определенной
     * временной зоне, она не сработает с Instant. А я думаю, нам тут нужна именно метка на временной шкале (раз уж
     * dto просит кол-во миллисекунд). Конечно можно перегнать условный LocalDateTime в Instant например, но это
     * не прям напрямую конвертируется. А вот так, как я предлагаю и конвертируется напрямую без аргументов и с этой
     * аннотацией работает. (Полный путь до покета оставил для ясности)
     */
    @NotNull
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(6) WITH TIME ZONE")
    private java.sql.Timestamp createdAt;

    @NotNull
    @Size(min = 8, max = 64)
    private String text;

}
