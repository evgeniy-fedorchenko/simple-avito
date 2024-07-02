package com.evgeniyfedorchenko.simpleavito.entity;

import com.evgeniyfedorchenko.simpleavito.dto.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    private int id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 10)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 10)
    private String lastName;

    @NotNull
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    @NotNull
    private Role role;

    @Nullable
    @Lob
    @Column(columnDefinition = "oid")
    private byte[] image;


    public boolean hasImage() {
        return image != null && (image.length > 0);
    }
}
