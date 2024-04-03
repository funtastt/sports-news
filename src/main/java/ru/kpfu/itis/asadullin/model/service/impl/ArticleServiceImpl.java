package ru.kpfu.itis.asadullin.model.service.impl;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.asadullin.util.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.repository.ArticleRepository;
import ru.kpfu.itis.asadullin.model.service.ArticleService;
import ru.kpfu.itis.asadullin.model.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleRepository.findAll().stream()
                .map(article -> mapToDto(article))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto getById(int id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return mapToDto(article);
    }

    @Override
    public void insert(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void update(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    private ArticleDto mapToDto(Article article) {
        return new ArticleDto(
                article.getTitle(),
                article.getContent(),
                article.getArticleId(),
                article.getSummary(),
                article.getAuthor().getUserId(),
                article.getAuthor().getFirstName() + " " + article.getAuthor().getLastName(),
                article.getAuthor().getProfilePicture(),
                article.getPublishTime(),
                article.getCategory(),
                article.getImageUrl(),
                article.getViews(),
                article.getLikes()
        );
    }
}
