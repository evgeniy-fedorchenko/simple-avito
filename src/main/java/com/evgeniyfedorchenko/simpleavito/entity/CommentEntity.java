package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Entity
@Table(name = "comments")
public class CommentEntity extends UserEntityRelated {

    @ManyToOne(optional = false)
    @JoinColumn(name = "ad_id")
    private AdEntity ad;

    @NotNull
    private Instant createdAt;

    @NotNull
    private String text;

}
