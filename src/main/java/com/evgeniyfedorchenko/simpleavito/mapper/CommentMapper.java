package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.cachedDto.CachedComment;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {UserMapper.class, UserController.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = UserMapper.class
)
public interface CommentMapper {

    @Mapping(target = "author", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "createdAt", expression = "java(commentEntity.getCreatedAt().toEpochMilli())")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorImage", expression = "java(userMapper.generateImageUrl(commentEntity.getAuthor(), UserController.IMAGE_PATH_SEGMENT))")
    Comment toDto(CommentEntity commentEntity);

    default Comments toDtos(List<CommentEntity> commentEntities) {
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
    CachedComment commentEntityToCachedComment(CommentEntity commentEntity);

    @InheritInverseConfiguration
    CommentEntity cachedCommentToCommentEntity(CachedComment cachedComment);

}
