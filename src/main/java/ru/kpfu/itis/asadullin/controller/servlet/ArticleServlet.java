package ru.kpfu.itis.asadullin.controller.servlet;

import com.google.gson.Gson;
import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.service.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.service.dao.impl.CommentDaoImpl;
import ru.kpfu.itis.asadullin.service.service.impl.ArticleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "articleServlet", urlPatterns = "/article")
@MultipartConfig
public class ArticleServlet extends HttpServlet {
    private ArticleDaoImpl articleDao;
    private CommentDaoImpl commentDao;
    private int articleId;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");

        articleDao = new ArticleDaoImpl();
        commentDao = new CommentDaoImpl();
        ArticleServiceImpl service = new ArticleServiceImpl();

        Article currentArticle = articleDao.getByTitle(title);
        ArticleDto articleDto = service.get(currentArticle);
        articleId = articleDao.findArticleId(currentArticle.getContent());

        List<Comment> comments = commentDao.getAllCommentsForArticle(articleId);

        req.setAttribute("article", articleDto);
        req.setAttribute("comments", comments);

        req.getRequestDispatcher("ftl/article.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commentText = req.getParameter("comment");

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int userId = findUserIdInCookie(req);

        Comment comment = new Comment(commentText, currentTime, userId, articleId);
        commentDao.insert(comment);

        List<Comment> updatedComments = commentDao.getAllCommentsForArticle(articleId);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(updatedComments);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.close();
    }

    private int findUserIdInCookie(HttpServletRequest req) {
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
