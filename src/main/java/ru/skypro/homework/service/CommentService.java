package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {

    Comments getComments(int id);

    Comment addComment(int id, CreateOrUpdateComment createOrUpdateComment);

    void deleteComment(int adId, int commentId);

    Comment updateComment(int adId, int commentId);
}
