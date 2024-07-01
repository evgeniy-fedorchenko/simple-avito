package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comments getComments(int id) {
        return new Comments();
    }

    @Override
    public Comment addComment(int id, CreateOrUpdateComment createOrUpdateComment) {
        return new Comment();
    }

    @Override
    public void deleteComment(int adId, int commentId) {
    }

    @Override
    public Comment updateComment(int adId, int commentId, CreateOrUpdateComment comment) {
        return new Comment();
    }
}
