package com.evgeniyfedorchenko.simpleavito.mapper;

import com.evgeniyfedorchenko.simpleavito.dto.Ads;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;

    public Comment toDto(CommentEntity commentEntity) {
        Comment comment = new Comment();
        UserEntity author = commentEntity.getAuthor();

        comment.setAuthor(author.getId());
        comment.setAuthorImage(author.hasImage() ? userMapper.generateImageUrl(author.getId()) : "no author image");
        comment.setAuthorFirstName(author.getFirstName());
        comment.setPk(commentEntity.getId());
        comment.setText(commentEntity.getText());

        return comment;
    }

    public Comments toDto(List<CommentEntity> commentEntities) {
        Comments comments = new Comments();

        comments.setCount(commentEntities.size());
        comments.setResults(commentEntities.stream().map(this::toDto).toList());

        LocalDateTime now = LocalDateTime.now();
        now.toInstant(ZoneOffset.MAX);

        java.sql.Timestamp a = new Timestamp(1212121);
        return comments;
    }
}
