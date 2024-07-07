package com.evgeniyfedorchenko.simpleavito.entity;

import com.evgeniyfedorchenko.simpleavito.dto.Role;
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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @ToString.Exclude
    private String password;

    @Nullable
    @Lob
    @Column(columnDefinition = "oid")
    @ToString.Exclude
    private byte[] image;

    @ToString.Exclude
    @Nullable
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdEntity> ads = new ArrayList<>();

    public boolean hasImage() {
        return image != null && image.length > 0;
    }

}
