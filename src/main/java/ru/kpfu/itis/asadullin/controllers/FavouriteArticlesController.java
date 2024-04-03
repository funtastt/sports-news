package ru.kpfu.itis.asadullin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.asadullin.model.entity.Favourite;
import ru.kpfu.itis.asadullin.model.service.ArticleService;
import ru.kpfu.itis.asadullin.model.service.FavouriteService;
import ru.kpfu.itis.asadullin.util.dto.ArticleDto;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/favourite")
public class FavouriteArticlesController {

    private final ArticleService articleService;
    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteArticlesController(ArticleService articleService, FavouriteService favouriteService) {
        this.articleService = articleService;
        this.favouriteService = favouriteService;
    }

    @GetMapping
    public String getFavouriteArticlesPage(Model model, HttpSession session) {
        List<ArticleDto> articlesDto = articleService.getAll();
        List<ArticleDto> favouriteArticles = new ArrayList<>();

        int userId = (int) session.getAttribute("userId");

        for (ArticleDto articleDto : articlesDto) {
            int articleId = articleService.findArticleId(articleDto.getContent());
            if (favouriteService.isFavoured(new Favourite(userId, articleId))) {
                favouriteArticles.add(articleDto);
            }
        }

        model.addAttribute("favouriteArticles", favouriteArticles);
        model.addAttribute("isLoggedIn", session.getAttribute("isLoggedIn"));

        return "ftl/favourite";
    }
}
