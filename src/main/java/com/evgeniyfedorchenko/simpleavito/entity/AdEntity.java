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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "ads")
public class AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Lob
    @Column(columnDefinition = "oid")
    @ToString.Exclude
    private byte[] image;

    @NotNull
    private Integer price;

    @NotNull
    private String title;

    @Nullable
    private String description;

    /* Это поле тут не обязательно, но я оставил, чтобы можно было указать настройки для него,
       например orphanRemoval вообще нельзя указать с той стороны этой связи */
    @Nullable
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    public boolean hasImage() {
        return image != null && image.length > 0;
    }

    public boolean hasDescription() {
        return description != null && description.isBlank();
    }

}
