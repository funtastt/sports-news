package ru.kpfu.itis.asadullin.controller.servlet;

import com.google.gson.Gson;
import org.cloudinary.json.JSONObject;
import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.dto.CommentDto;
import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.entity.ArticleLike;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.service.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.service.dao.impl.ArticleLikeImpl;
import ru.kpfu.itis.asadullin.service.dao.impl.CommentDaoImpl;
import ru.kpfu.itis.asadullin.service.service.impl.ArticleServiceImpl;
import ru.kpfu.itis.asadullin.service.service.impl.CommentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

// TODO: до конца оформить внешний вид комментария
@WebServlet(name = "articleServlet", urlPatterns = "/article")
@MultipartConfig
public class ArticleServlet extends HttpServlet {
    private ArticleDaoImpl articleDao;
    private Article currentArticle;
    private CommentServiceImpl commentService;
    private int articleId;
    private int userId;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");

        articleDao = new ArticleDaoImpl();
        commentService = new CommentServiceImpl();
        ArticleServiceImpl service = new ArticleServiceImpl();

        currentArticle = articleDao.getByTitle(title);
        currentArticle.setViews(currentArticle.getViews() + 1);
        articleDao.update(currentArticle);
        ArticleDto articleDto = service.getById(currentArticle);


        articleId = articleDao.findArticleId(currentArticle.getContent());
        userId = findUserIdInCookie(req);

        ArticleLikeImpl likeDao = new ArticleLikeImpl();
        boolean isArticleLiked = likeDao.isArticleLiked(new ArticleLike(userId,articleId));

        List<CommentDto> comments = commentService.getAllCommentsForArticle(articleId);

        req.setAttribute("article", articleDto);
        req.setAttribute("comments", comments);
        req.setAttribute("isArticleLiked", isArticleLiked);

        req.getRequestDispatcher("ftl/article.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("like".equals(action)) {
            sendLike(resp);
        } else if ("comment".equals(action)) {
            sendComment(req, resp);
        }
    }

    private void sendLike(HttpServletResponse resp) throws IOException {
        ArticleLikeImpl likeDao = new ArticleLikeImpl();
        ArticleLike like = new ArticleLike(userId, articleId);

        boolean isArticleLiked = likeDao.isArticleLiked(like);

        if (isArticleLiked) {
            likeDao.delete(like);
        } else {
            likeDao.insert(like);
        }

        int count = likeDao.getLikesCount(articleId);
        currentArticle.setLikes(count);
        articleDao.update(currentArticle);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Создайте объект JSON для отправки
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("likesCount", count);
        responseJSON.put("isArticleLiked", !isArticleLiked);

        resp.getWriter().print(responseJSON);
    }

    private void sendComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String commentText = req.getParameter("comment");

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment(commentText, currentTime, userId, articleId);
        commentService.insert(comment);

        List<CommentDto> updatedComments = commentService.getAllCommentsForArticle(articleId);

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
