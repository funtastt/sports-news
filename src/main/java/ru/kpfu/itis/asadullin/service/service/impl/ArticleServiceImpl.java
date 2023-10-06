package ru.kpfu.itis.asadullin.service.service.impl;

import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.service.dao.Dao;
import ru.kpfu.itis.asadullin.service.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.service.service.Service;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleServiceImpl implements Service<Article, ArticleDto> {
    private final Dao<Article> dao = new ArticleDaoImpl();

    @Override
    public List<ArticleDto> getAll() {
        return dao.getAll().stream().map(
                a -> new ArticleDto(a.getTitle(), a.getContent(), a.getAuthor(), a.getPublishTime(), a.getCategory(), a.getSourceUrl(), a.getImageUrl(), a.getViews(), a.getLikes())
        ).collect(Collectors.toList());
    }

    @Override
    public ArticleDto get(Article article) {
        return new ArticleDto(article.getTitle(), article.getContent(), article.getAuthor(), article.getPublishTime(), article.getCategory(), article.getSourceUrl(), article.getImageUrl(), article.getViews(), article.getLikes());
    }

    @Override
    public void insert(Article article) {
        dao.insert(article);
    }

    @Override
    public void update(Article article) {
        dao.update(article);
    }

    @Override
    public void delete(Article article) {
        dao.delete(article);
    }
}
