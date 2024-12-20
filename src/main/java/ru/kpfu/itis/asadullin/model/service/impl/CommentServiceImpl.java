package ru.kpfu.itis.asadullin.model.service.impl;

import ru.kpfu.itis.asadullin.model.dao.impl.CommentDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.LikeDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.controller.util.dto.CommentDto;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.model.entity.Like;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.service.Service;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceImpl implements Service<Comment, CommentDto> {
    CommentDaoImpl commentDao = new CommentDaoImpl();
    UserDaoImpl userDao = new UserDaoImpl();

    private final int currentUserId;

    public CommentServiceImpl(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public List<CommentDto> getAll() {
        List<Comment> comments = commentDao.getAll();
        List<CommentDto> commentsDto = new ArrayList<>();

        for (Comment comment : comments) {
            commentsDto.add(commentToCommentDto(comment));
        }

        return commentsDto;
    }


    @Override
    public CommentDto getById(int id) {
        return commentToCommentDto(commentDao.getById(id));
    }

    private CommentDto commentToCommentDto(Comment comment) {
        LikeDaoImpl likeDao = new LikeDaoImpl();
        User author = userDao.getById(comment.getAuthorId());

        return new CommentDto(
                comment.getText(),
                comment.getSendingTime(),
                comment.getLikes(),
                comment.getCommentId(),
                author.getUserId(),
                author.getUsername(),
                author.getProfilePicture(),
                comment.getArticleId(),
                likeDao.isLiked(new Like(currentUserId, comment.getCommentId(), false)));
    }

    @Override
    public void insert(Comment comment) {
        commentDao.insert(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    public List<CommentDto> getAllCommentsForArticle(int articleId) {
        List<CommentDto> allComments = getAll();
        List<CommentDto> result = new ArrayList<>();

        for (CommentDto comment: allComments) {
            if (comment.getArticleId() == articleId) result.add(comment);
        }

        return result;
    }
}
