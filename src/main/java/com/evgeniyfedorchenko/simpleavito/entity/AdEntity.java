package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @Nullable
    @OneToMany(mappedBy = "adEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CommentEntity> comments;

    @Lob
    @Column(columnDefinition = "oid")
    private byte[] image;

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

    public boolean hasImage() {
        return image != null && image.length > 0 && mediaType != null;
    }

    public boolean hasDescription() {
        return !description.isBlank();
    }

}
