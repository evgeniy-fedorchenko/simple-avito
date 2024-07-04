package com.evgeniyfedorchenko.simpleavito.entity;

import com.evgeniyfedorchenko.simpleavito.dto.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Email
    @Column(unique = true)
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

    @Size(min = 5, max = 25)
    private String mediaType;

    @Nullable
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AdEntity> ads = new ArrayList<>();

    public boolean hasImage() {
        return image != null && image.length > 0 && mediaType != null;
    }

    /**
     * Метод добавляет переданный в параметре объект в коллекцию {@code this.ads}, а так же устанавливает
     * объект {@code this} в поле {@link AdEntity#author} у переданного экземпляра {@code AdEntity ad}
     * Добавление происходит локально, необходимо обновление в базе данных
     * @param ad объект, который нужно добавить в коллекцию {@code this.ads}
     * @return объект {@link UserEntity} с обновленной коллекцией <b>ads</b>, в которую локально добавлен
     * переданный в параметре объект {@link AdEntity ad}
     */
    public UserEntity addAd(AdEntity ad) {
        ad.setAuthor(this);
        ads.add(ad);
        return this;
    }

    /**
     * Метод удаляет переданный в параметре объект из коллекции {@code this.ads}, а так же устанавливает
     * {@code null} в поле {@link AdEntity#author} у переданного экземпляра класса {@code AdEntity ad}
     * Удаление происходит локально, необходимо обновление в базе данных
     * @param ad объект, который нужно удалить из коллекции {@code this.ads}
     * @return объект {@link UserEntity} с обновленной коллекцией <b>ads</b>, из которой локально удален
     * переданный в параметре объект {@link AdEntity}. Если переданный объект не был найден в коллекции,
     * то коллекция не изменится
     */
    public UserEntity removeStudent(AdEntity ad) {
        ad.setAuthor(null);
        this.ads.remove(ad);
        return this;
    }
}
