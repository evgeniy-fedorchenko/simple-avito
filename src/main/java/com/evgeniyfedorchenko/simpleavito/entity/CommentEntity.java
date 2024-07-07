package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ad_id")
    private AdEntity ad;

    /* Решил вообще убрать работу со временем. По заданию dto должно возвращать кол-во миллисекунд с начала
       эпохи. Так что в бд и будем хранить это кол-во миллисекунд и задавать как System.currentTimeMillis()  */
    @NotNull
    private Long createdAt;

    @NotNull
    private String text;

}
