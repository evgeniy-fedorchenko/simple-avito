package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateComment;

import java.util.Optional;

public interface CommentService {

    Optional<Comments> getComments(long adId);

    Comment addComment(long id, CreateOrUpdateComment createOrUpdateComment);

    void deleteComment(long adId, long commentId);

    Comment updateComment(long adId, long commentId, CreateOrUpdateComment comment);
}
