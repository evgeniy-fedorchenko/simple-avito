package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateComment;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.entity.CommentEntity;
import com.evgeniyfedorchenko.simpleavito.mapper.CommentMapper;
import com.evgeniyfedorchenko.simpleavito.repository.AdRepository;
import com.evgeniyfedorchenko.simpleavito.repository.CommentRepository;
import com.evgeniyfedorchenko.simpleavito.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Comments> getComments(long adId) {
        return adRepository.findById(adId).map(adEntity -> commentMapper.toDtos(adEntity.getComments()));
    }

    @Override
    @Transactional
    public Optional<Comment> addComment(long adId, CreateOrUpdateComment createOrUpdateComment) {
        Optional<AdEntity> adEntityOpt = adRepository.findById(adId);
        if (adEntityOpt.isEmpty()) {
            return Optional.empty();
        }
        AdEntity adEntity = adEntityOpt.get();

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(userRepository.findByEmail(authService.getUsername()));
        commentEntity.setText(createOrUpdateComment.getText());
        Instant now = Instant.now();
        log.info("now: " + now);
        commentEntity.setCreatedAt(now);
        commentEntity.setAd(adEntity);

        List<CommentEntity> comments = adEntity.getComments();
        comments.add(commentEntity);
        adEntity.setComments(comments);

        CommentEntity savedComment = commentRepository.save(commentEntity);

        AdEntity savedAd = adRepository.save(adEntity);

        log.debug("Created comment {} at Ad {}", savedComment, savedAd);
        return Optional.of(commentMapper.toDto(savedComment));
    }

    @Override
    @Transactional
    @PreAuthorize(value = "@authChecker.hasPermissionToEdit(#commentId, @commentRepository)")
    public boolean deleteComment(long adId, long commentId) {

        Optional<CommentEntity> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty() || commentOpt.get().getAd().getId() != adId) {
            return false;
        }

        commentRepository.deleteById(commentId);
        log.debug("Deleted comment {}", commentOpt.get());
        return true;
    }

    @Override
    @Transactional
    @PreAuthorize(value = "@authChecker.hasPermissionToEdit(#commentId, @commentRepository)")
    public Optional<Comment> updateComment(long adId, long commentId, CreateOrUpdateComment comment) {

        Optional<CommentEntity> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty() || commentOpt.get().getAd().getId() != adId) {
            return Optional.empty();
        }

        CommentEntity commentEntity = commentOpt.get();
        commentEntity.setText(comment.getText());
        CommentEntity savedComment = commentRepository.save(commentEntity);

        log.debug("Updated comment {}", savedComment);
        return Optional.of(commentMapper.toDto(savedComment));
    }

}
