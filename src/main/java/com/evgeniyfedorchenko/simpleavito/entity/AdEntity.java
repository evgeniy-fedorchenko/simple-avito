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

    /**
     * В контексте связи {@code ads 1:М comments} AdEntity установлена главной сущностью.
     * Это сделано потому что в таблице {@code comments} нет поля-ссылки на таблицу {@code ads} и нет
     * необходимости его иметь, так как нет нужды получать объявление по какому-то его комментарию.
     * А вот получать комментарии конкретного объявления нужно постоянно
     * (тем не менее колонка {@code ids_id} в таблице comments все равно существует)
     * <p>
     * <ul>
     *   <li><b>Синхронизация:</b> {@code orphanRemoval = true} защищает от появления осиротевших объектов в таблице
     *       {@code comments}, а {@code cascade = CascadeType.ALL} согласует все операции в связанной сущности,
     *       если они были проведены в родительской. Например, при локальном создании {@link CommentEntity}, добавлении
     *       его в список какой-то {@link AdEntity} и сохранении этой {@link AdEntity} объект комментария автоматически
     *       создастся и сохраниться в своей таблице тоже</li>
     *
     *   <li><b>Ссылочная целостность:</b> changeSet-id=4 (его пока не существует) запрещает сохранение в таблицу
     *       {@code comments} <i>изначально</i> сиротских объектов</li>
     *
     *   <li><b>Память:</b> {@code fetch = FetchType.LAZY} защищает от перегрузки памяти тысячами комментариев</li>
     * </ul>
     * </p>
     *
     *
     */
    @Nullable
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id")
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    public boolean hasImage() {
        return image != null && image.length > 0 && mediaType != null;
    }

    public boolean hasDescription() {
        return !description.isBlank();
    }

}
