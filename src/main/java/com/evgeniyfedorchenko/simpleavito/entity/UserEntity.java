package com.evgeniyfedorchenko.simpleavito.entity;

import com.evgeniyfedorchenko.simpleavito.service.ImageServiceImpl;
import io.imagekit.sdk.models.results.Result;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

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

    /**
     * Поле содержащее {@code username} этого пользователя
     */
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
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Role role;

    @NotNull
    @ToString.Exclude
    private String password;

    /**
     * Поле, хранящее комбинированных идентификатор изображения.
     * Представлен как конкатенация {@link Result#getFileId()} + константа {@link ImageServiceImpl#SEPARATOR} + {@link Result#getUrl()}
     * @example {@code 669e774ce375273f6047bad0<SEP>https://ik.imagekit.io/nyxshvudx/3f8cd931-b6cd-49bf-9585-6b15f598040e_xYFU60zma}
     */
    @Nullable
    private String imageCombinedId;

    // TODO 23.07.2024 01:12: Warning! При автоматическом удалении списка объявлений фотки этих объявлений не удаляются с сервера!
    /**
     * Список связанных экземпляров сущности {@link AdEntity} как {@code this 1:M AdEntity}. Настройки:
     * <lu>
     *     <li>{@code cascade} Каскадные операции для сохранения, слияния, удаления и обновления</li>
     *     <li>{@code orphanRemoval = true} автоматическое удаление осиротевших сущностей</li>
     *     <li>fetch = FetchType.LAZY</li> список реальных объектов необходимо загружать явно и <b>только внутри транзакции</b>
     * </lu>
     */
    @ToString.Exclude
    @Nullable
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AdEntity> ads = new ArrayList<>();

    public boolean hasImage() {
        return imageCombinedId != null;
    }

}
