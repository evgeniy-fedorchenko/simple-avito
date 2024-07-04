package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.entity.AdEntity;
import com.evgeniyfedorchenko.simpleavito.repository.AdRepository;
import com.evgeniyfedorchenko.simpleavito.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateComment;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;

    @Override
    public Optional<Comments> getComments(long adId) {
        return Optional.empty();
    }

    @Override
    public Comment addComment(long id, CreateOrUpdateComment createOrUpdateComment) {
        return new Comment();
    }

    @Override
    public void deleteComment(long adId, long commentId) {
    }

    @Override
    public Comment updateComment(long adId, long commentId, CreateOrUpdateComment comment) {
        return new Comment();
    }
}
