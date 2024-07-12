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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setAuthor(userRepository.findByEmail(authService.getUsername()));
        commentEntity.setText(createOrUpdateComment.getText());
        commentEntity.setCreatedAt(Instant.now());
        CommentEntity savedComment = commentRepository.save(commentEntity);

        log.debug("Created comment {}", savedComment);
        return Optional.of(commentMapper.toDto(savedComment));
    }

    @Override
    @Transactional
    public boolean deleteComment(long adId, long commentId) {
        Optional<CommentEntity> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty() || commentOpt.get().getAd().getId() != adId) {
            return false;
        }

        CommentEntity commentEntity = commentOpt.get();
        throwIfForbidden(commentEntity);

        commentRepository.deleteById(commentId);
        log.debug("Deleted comment {}", commentOpt.get());
        return true;
    }

    @Override
    @Transactional
    public Optional<Comment> updateComment(long adId, long commentId, CreateOrUpdateComment comment) {

        Optional<CommentEntity> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty() || commentOpt.get().getAd().getId() != adId) {
            return Optional.empty();
        }

        CommentEntity commentEntity = commentOpt.get();
        throwIfForbidden(commentEntity);

        commentEntity.setText(comment.getText());
        CommentEntity savedComment = commentRepository.save(commentOpt.get());

        log.debug("Updated comment {}", savedComment);
        return Optional.of(commentMapper.toDto(savedComment));
    }

    /**
     * Метод проверяет, если у авторизованного в данный момент юзера права изменять/удалять соответсвующий комментарий
     * @param targetComment сущность комментария для проверки
     * @throws AccessDeniedException если авторизованному в данный момент пользователю
     *                               запрещено изменять/удалять комментарий, переданный в параметре
     */
    private void throwIfForbidden(CommentEntity targetComment) {

        if (!targetComment.getAuthor().getEmail().equals(authService.getUsername()) && !authService.isAdmin()) {
            throw new AccessDeniedException("%s don't have permission to remove someone else's ad"
                    .formatted(targetComment.getAuthor().getEmail()));
        }

    }

}
