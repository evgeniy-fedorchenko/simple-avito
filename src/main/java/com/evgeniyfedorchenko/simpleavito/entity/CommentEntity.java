package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

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

    @NotNull
    private Instant createdAt;

    @NotNull
    private String text;

}
