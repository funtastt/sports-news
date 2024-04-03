package ru.kpfu.itis.asadullin.model.service;

import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.util.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> getAll();

    ArticleDto getById(int id);

    void insert(Article article);

    void update(Article article);

    void delete(Article article);
}
