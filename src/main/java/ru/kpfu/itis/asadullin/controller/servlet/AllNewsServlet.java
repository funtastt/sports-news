package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.controller.util.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;

import javax.servlet.ServletConfig;
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
    ArticleServiceImpl articleService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        articleService = (ArticleServiceImpl) config.getServletContext().getAttribute("articleDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ArticleDto> articlesDto = articleService.getAll();
        articlesDto.sort((o1, o2) -> o2.getViews() - o1.getViews());

        ArticleDto mostViewed = articlesDto.remove(0);

        String sortType = req.getParameter("sort");

        if (sortType == null) {
            sortType = "Newest";
        } else {
            switch (sortType) {
                case "1":
                    sortType = "Most viewed";
                    break;
                case "2":
                    sortType = "Most liked";
                    break;
                default:
                    sortType = "Newest";
                    break;
            }
        }

        req.setAttribute("mostViewed", mostViewed);
        req.setAttribute("allNews", articlesDto);
        req.setAttribute("isLoggedIn", isLoggedIn(req));
        req.setAttribute("sortType", sortType);

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

    static boolean isLoggedIn(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    return true;
                }
            }
        }
        return false;
    }
}
