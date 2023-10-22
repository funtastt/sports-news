package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.FavouriteDaoImpl;
import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.entity.Favourite;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.findUserIdInCookie;
import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.isLoggedIn;

@WebServlet(name = "favouriteArticlesServlet", urlPatterns = "/favourite")
public class FavouriteArticlesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ArticleDto> articlesDto = new ArticleServiceImpl().getAll();
        List<ArticleDto> favouriteArticles = new ArrayList<>();
        FavouriteDaoImpl favouriteDao = new FavouriteDaoImpl();
        ArticleDaoImpl articleDao = new ArticleDaoImpl();

        int userId = findUserIdInCookie(req);

        for (ArticleDto articleDto : articlesDto) {
            int articleId = articleDao.findArticleId(articleDto.getContent());
            if (favouriteDao.isFavoured(new Favourite(userId, articleId))) {
                favouriteArticles.add(articleDto);
            }
        }

        req.setAttribute("favouriteArticles", favouriteArticles);
        req.setAttribute("isLoggedIn", isLoggedIn(req));

        req.getRequestDispatcher("ftl/favourite.ftl").forward(req, resp);
    }
}
