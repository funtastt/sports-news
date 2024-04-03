package ru.kpfu.itis.asadullin.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.asadullin.util.dto.CommentDto;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.model.repositories.CommentRepository;
import ru.kpfu.itis.asadullin.model.service.CommentService;
import ru.kpfu.itis.asadullin.model.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(int id) {
        Comment comment = commentRepository.getById(id);
        return mapToDto(comment);
    }

    @Override
    public void insert(Comment comment) {
        commentRepository.insert(comment.getText(), comment.getSendingTime(), comment.getAuthorId(), comment.getArticleId());
    }

    @Override
    public void update(Comment comment) {
        commentRepository.update(comment.getCommentId(), comment.getText(), comment.getSendingTime(), comment.getLikes());
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment.getCommentId());
    }

    @Override
    public List<CommentDto> getAllCommentsForArticle(int articleId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getArticleId() == articleId)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment) {
        return new CommentDto(
                comment.getText(),
                comment.getSendingTime(),
                comment.getLikes(),
                comment.getCommentId(),
                comment.getAuthorId(),
                userService.getById(comment.getAuthorId()).getFirstName(),
                userService.getById(comment.getAuthorId()).getProfilePicture(),
                comment.getArticleId(),
                false // You need to implement checking if the current user has liked this comment
        );
    }
}
