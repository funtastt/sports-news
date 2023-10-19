package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "newsServlet", urlPatterns = "/news")
public class AllNewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ArticleDto> articlesDto = new ArticleServiceImpl().getAll();
        articlesDto.sort((o1, o2) -> o2.getPublishTime().compareTo(o1.getPublishTime()));

        String category = req.getParameter("category");

        if (category != null && !category.isEmpty()) {
            articlesDto.removeIf(articleDto -> !articleDto.getCategory().equals(category));
        }

        String sortTypeString = req.getParameter("sort");
        String sortTypeResp = "Newest";;

        if (sortTypeString != null) {
            int sortType = Integer.parseInt(sortTypeString);

            switch (sortType) {
                case 0:
                    articlesDto.sort((o1, o2) -> o2.getPublishTime().compareTo(o1.getPublishTime()));
                    sortTypeResp = "Newest";
                    break;
                case 1:
                    articlesDto.sort((o1, o2) -> o2.getViews() - o1.getViews());
                    sortTypeResp = "Most viewed";
                    break;
                case 2:
                    articlesDto.sort((o1, o2) -> o2.getLikes() - o1.getLikes());
                    sortTypeResp = "Most liked";
                    break;
            }
        }

        ArticleDto mostViewed = articlesDto.remove(0);

        req.setAttribute("mostViewed", mostViewed);
        req.setAttribute("allNews", articlesDto);
        req.setAttribute("sortType", sortTypeResp);

        req.getRequestDispatcher("ftl/news.ftl").forward(req, resp);
    }

    static int findUserIdInCookie(HttpServletRequest req) {
        int userId = -1;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        return userId;
    }
}
