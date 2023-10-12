package ru.kpfu.itis.asadullin.service.service.impl;

import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.service.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.service.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.service.service.Service;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleServiceImpl implements Service<Article, ArticleDto> {
    private final ArticleDaoImpl articleDao = new ArticleDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public List<ArticleDto> getAll() {
        return articleDao.getAll().stream().map(
                a -> new ArticleDto(a.getTitle(), a.getContent(), a.getSummary(), userDao.getById(a.getAuthorId()).getFirstName() + " " + userDao.getById(a.getAuthorId()).getLastName() + " (" + userDao.getById(a.getAuthorId()).getUsername() + ")", userDao.getById(a.getAuthorId()).getProfilePicture(), a.getPublishTime(), a.getCategory(), a.getImageUrl(), a.getViews(), a.getLikes())
        ).collect(Collectors.toList());
    }

    @Override
    public ArticleDto getById(Article article) {
        return new ArticleDto(article.getTitle(), article.getContent(), article.getSummary(), userDao.getById(article.getAuthorId()).getFirstName() + " " + userDao.getById(article.getAuthorId()).getLastName() + " (" + userDao.getById(article.getAuthorId()).getUsername() + ")", userDao.getById(article.getAuthorId()).getProfilePicture(), article.getPublishTime(), article.getCategory(), article.getImageUrl(), article.getViews(), article.getLikes());
    }

    @Override
    public void insert(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }
}
