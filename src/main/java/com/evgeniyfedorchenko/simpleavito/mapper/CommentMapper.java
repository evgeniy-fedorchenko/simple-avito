package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedComment;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {UserMapper.class, UserController.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = UserMapper.class
)
@NoArgsConstructor  // Такой набор аннотаций чисто для теста, чтобы все разрешить и не париться. Потом конечно это уберу и оставлю минимум
@Data
public abstract class CommentMapper {

    public UserMapper userMapper;

    @Mapping(target = "author", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "createdAt", expression = "java(commentEntity.getCreatedAt().toEpochMilli())")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorImage", expression = "java(userMapper.generateImageUrl(commentEntity.getAuthor()))")
    public abstract Comment toDto(CommentEntity commentEntity);

    public Comments toDtos(List<CommentEntity> commentEntities) {
        Comments comments = new Comments();
        comments.setCount(commentEntities.size());
        comments.setResults(commentEntities.stream().map(this::toDto).toList());
        return comments;
    }

    @Mapping(source = "ad.id", target = "adId")
    @Mapping(source = "ad.imageCombinedId", target = "adImageCombinedId")
    @Mapping(source = "ad.price", target = "adPrice")
    @Mapping(source = "ad.title", target = "adTitle")
    @Mapping(source = "ad.description", target = "adDescription")
    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.email", target = "authorEmail")
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    @Mapping(source = "author.phone", target = "authorPhone")
    @Mapping(source = "author.role", target = "authorRole")
    @Mapping(source = "author.password", target = "authorPassword")
    @Mapping(source = "author.imageCombinedId", target = "authorImageCombinedId")
    public abstract CachedComment commentEntityToCachedComment(CommentEntity commentEntity);

    @InheritInverseConfiguration
    public abstract CommentEntity cachedCommentToCommentEntity(CachedComment cachedComment);


    /**
     * Временный фикс заключается в том, чтобы продублировать метод, который генерит ссылку на фотку в этот маппер
     * и вызывать его. Тогда не нужно выполнять инжект UserMapper.
     * Таким образом удается запустить код
     */
//    default @Nullable String generateImageUrl(UserEntity userEntity, String... pathSegments) {
//        if (!userEntity.hasImage()) {
//            return null;
//        }
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
//                .path(UserController.BASE_USER_URI)
//                .pathSegment(String.valueOf(userEntity.getId()));
//        if (pathSegments != null) {
//            Arrays.stream(pathSegments).forEach(uriComponentsBuilder::pathSegment);
//        }
//        return uriComponentsBuilder.build().toUriString();
//    }

}
