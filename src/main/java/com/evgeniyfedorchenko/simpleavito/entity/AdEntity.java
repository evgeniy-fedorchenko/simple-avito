package com.evgeniyfedorchenko.simpleavito.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Lob
    @Column(columnDefinition = "oid")
    private byte[] image;

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
        return image != null && image.length > 0;
    }

    public boolean hasDescription() {
        return !description.isBlank();
    }

}
