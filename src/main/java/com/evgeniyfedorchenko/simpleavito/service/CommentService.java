package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateComment;

import java.util.Optional;

public interface CommentService {

    Optional<Comments> getComments(long adId);

    Optional<Comment> addComment(long adId, CreateOrUpdateComment createOrUpdateComment);

    boolean deleteComment(long adId, long commentId);

    Optional<Comment> updateComment(long adId, long commentId, CreateOrUpdateComment comment);
}
