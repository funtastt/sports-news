package ru.kpfu.itis.asadullin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.asadullin.model.service.ArticleService;
import ru.kpfu.itis.asadullin.util.dto.ArticleDto;
import ru.kpfu.itis.asadullin.util.constants.ServerResources;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(ServerResources.ALL_NEWS_PAGE)
public class AllNewsController {
    private final ArticleService articleService;

    @Autowired
    public AllNewsController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public String showAllNews(HttpServletRequest request, Model model) {
        List<ArticleDto> articlesDto = articleService.getAll();
        articlesDto.sort((o1, o2) -> o2.getViews() - o1.getViews());

        ArticleDto mostViewed = articlesDto.isEmpty() ? null : articlesDto.remove(0);

        String sortType = request.getParameter("sort");
        if (sortType == null) {
            sortType = ServerResources.NEWEST;
        } else {
            switch (sortType) {
                case "1":
                    sortType = ServerResources.MOST_VIEWED;
                    break;
                case "2":
                    sortType = ServerResources.MOST_LIKED;
                    break;
                default:
                    sortType = ServerResources.NEWEST;
                    break;
            }
        }

        model.addAttribute(ServerResources.MOST_VIEWED, mostViewed);
        model.addAttribute(ServerResources.ALL_NEWS, articlesDto);
        model.addAttribute(ServerResources.SORT_TYPE, sortType);

        return "news";
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(ServerResources.USER_ID)) {
                    return true;
                }
            }
        }
        return false;
    }
}
