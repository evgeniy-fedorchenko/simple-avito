package com.evgeniyfedorchenko.simpleavito.entity;

import com.evgeniyfedorchenko.simpleavito.service.ImageServiceImpl;
import io.imagekit.sdk.models.results.Result;
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
@ToString(callSuper = true)
@Entity
@Table(name = "ads")
public final class AdEntity extends UserEntityRelated {

    /**
     * Поле, хранящее комбинированных идентификатор изображения.
     * Представлен как конкатенация {@link Result#getFileId()} + константа {@link ImageServiceImpl#SEPARATOR} + {@link Result#getUrl()}
     * @example {@code 669e774ce375273f6047bad0<SEP>https://ik.imagekit.io/nyxshvudx/3f8cd931-b6cd-49bf-9585-6b15f598040e_xYFU60zma}
     */
    @Nullable
    private String imageCombinedId;

    @NotNull
    private Integer price;

    @NotNull
    private String title;

    @Nullable
    private String description;

    /**
     * Список связанных экземпляров сущности {@link CommentEntity} как {@code this 1:M CommentEntity}. Настройки:
     * <lu>
     *     <li>{@code cascade} Каскадные операции для сохранения, слияния, удаления и обновления</li>
     *     <li>{@code orphanRemoval = true} автоматическое удаление осиротевших сущностей</li>
     *     <li>fetch = FetchType.LAZY</li> список реальных объектов необходимо загружать явно и <b>только внутри транзакции</b>
     * </lu>
     */
    @Nullable
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    public boolean hasImage() {
        return imageCombinedId != null;
    }

}
