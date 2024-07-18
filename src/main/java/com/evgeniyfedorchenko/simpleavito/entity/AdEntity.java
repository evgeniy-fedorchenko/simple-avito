package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Entity
@Table(name = "ads")
public class AdEntity extends UserEntityRelated {

    @Nullable
     // TODO 17.07.2024 23:24
    private String imageUuid;

    @NotNull
    private Integer price;

    @NotNull
    private String title;

    @Nullable
    private String description;

    @Nullable
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    public boolean hasImage() {
        return imageUuid != null;
    }

}
