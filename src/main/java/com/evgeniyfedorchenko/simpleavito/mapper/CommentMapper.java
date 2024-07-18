package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.controller.UserController;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Comment toDto(CommentEntity commentEntity) {
        Comment comment = new Comment();
        UserEntity author = commentEntity.getAuthor();

        comment.setAuthor(author.getId());
        comment.setAuthorFirstName(author.getFirstName());
        comment.setCreatedAt(commentEntity.getCreatedAt().getEpochSecond());
        comment.setPk(commentEntity.getId());
        comment.setText(commentEntity.getText());

        comment.setAuthorImage(author.hasImage()
                ? userMapper.generateImageUrl(author.getId(), UserController.IMAGE_PATH_SEGMENT)
                : null
        );

        return comment;
    }

    public Comments toDtos(List<CommentEntity> commentEntities) {
        Comments comments = new Comments();

        comments.setCount(commentEntities.size());
        comments.setResults(commentEntities.stream().map(this::toDto).toList());

        return comments;
    }
}
