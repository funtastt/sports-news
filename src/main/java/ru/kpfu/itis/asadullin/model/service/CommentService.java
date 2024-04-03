package ru.kpfu.itis.asadullin.model.service;

import ru.kpfu.itis.asadullin.util.dto.CommentDto;
import ru.kpfu.itis.asadullin.model.entity.Comment;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAll();

    CommentDto getById(int id);

    void insert(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);

    List<CommentDto> getAllCommentsForArticle(int articleId);
}
