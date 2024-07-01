package com.evgeniyfedorchenko.simpleavito.service;

import com.evgeniyfedorchenko.simpleavito.dto.Comments;
import com.evgeniyfedorchenko.simpleavito.dto.Comment;
import com.evgeniyfedorchenko.simpleavito.dto.CreateOrUpdateComment;

public interface CommentService {

    Comments getComments(int id);

    Comment addComment(int id, CreateOrUpdateComment createOrUpdateComment);

    void deleteComment(int adId, int commentId);

    Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment);
}
