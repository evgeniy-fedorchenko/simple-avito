package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedComment;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = UserController.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Setter
@NoArgsConstructor
public abstract class CommentMapper {

    @Mapping(target = "author", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "createdAt", expression = "java(commentEntity.getCreatedAt().toEpochMilli())")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorImage", expression = "java(this.generateImageUrlForComment(commentEntity))")
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

    String generateImageUrlForComment(CommentEntity commentEntity) {
        UserEntity author = commentEntity.getAuthor();
        return this.generateImageUrl(author, UserController.IMAGE_PATH_SEGMENT);
    }

    private @Nullable String generateImageUrl(UserEntity userEntity, String... pathSegments) {
        if (!userEntity.hasImage()) {
            return null;
        }
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .path(UserController.BASE_USER_URI)
                .pathSegment(String.valueOf(userEntity.getId()));
        if (pathSegments != null) {
            Arrays.stream(pathSegments).forEach(uriComponentsBuilder::pathSegment);
        }
        return uriComponentsBuilder.build().toUriString();
    }
}
