package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString // todo переопределить везде
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
    private byte[] image;

    @Size(min = 5, max = 25)
    private String mediaType;

    @NotNull
    @Min(0)
    @Max(10_000_000)
    private int price;

    @NotNull
    @Size(min = 4, max = 32)
    private String title;

    @Size(min = 8, max = 64)
    private String description;

    @Nullable
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ads_id")
    private List<CommentEntity> comments = new ArrayList<>();

    public boolean hasImage() {
        return image != null && image.length > 0 && mediaType != null;
    }

    public boolean hasDescription() {
        return !description.isBlank();
    }

}
