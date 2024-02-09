package ru.kpfu.itis.asadullin.controller.servlet;

import com.google.gson.Gson;
import org.cloudinary.json.JSONObject;
import ru.kpfu.itis.asadullin.model.dao.impl.*;
import ru.kpfu.itis.asadullin.controller.util.dto.ArticleDto;
import ru.kpfu.itis.asadullin.controller.util.dto.CommentDto;
import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.model.entity.Favourite;
import ru.kpfu.itis.asadullin.model.entity.Like;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;
import ru.kpfu.itis.asadullin.model.service.impl.CommentServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.findUserIdInCookie;
import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.isLoggedIn;

@WebServlet(name = "articleServlet", urlPatterns = "/article")
@MultipartConfig
public class ArticleServlet extends HttpServlet {
    private Article currentArticle;
    private CommentServiceImpl commentService;
    private CommentDaoImpl commentDao;
    private int articleId;
    private int userId;
    ArticleServiceImpl articleService;
    ArticleDaoImpl articleDao;
    FavouriteDaoImpl favouriteDao;
    LikeDaoImpl likeDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        articleService = (ArticleServiceImpl) config.getServletContext().getAttribute("articleService");
        articleDao = (ArticleDaoImpl) config.getServletContext().getAttribute("articleDao");
        commentDao = (CommentDaoImpl) config.getServletContext().getAttribute("commentDao");
        likeDao = (LikeDaoImpl) config.getServletContext().getAttribute("likeDao");
        favouriteDao = (FavouriteDaoImpl) config.getServletContext().getAttribute("favouriteDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        articleId = Integer.parseInt(req.getParameter("articleId"));

        commentService = new CommentServiceImpl(userId);

        currentArticle = articleDao.getById(articleId);

        currentArticle.setViews(currentArticle.getViews() + 1);
        articleDao.update(currentArticle);
        ArticleDto articleDto = articleService.getById(articleId);

        userId = findUserIdInCookie(req);

        boolean isArticleLiked = likeDao.isLiked(new Like(userId,articleId, true));

        boolean isArticleFavoured = favouriteDao.isFavoured(new Favourite(userId, articleId));

        List<CommentDto> comments = commentService.getAllCommentsForArticle(articleId);
        comments.sort(Comparator.comparing(CommentDto::getSendingTime));

        req.setAttribute("article", articleDto);
        req.setAttribute("comments", comments);
        req.setAttribute("isArticleLiked", isArticleLiked);
        req.setAttribute("isArticleLiked", isArticleLiked);
        req.setAttribute("isLoggedIn", isLoggedIn(req));
        req.setAttribute("isArticleFavoured", isArticleFavoured);

        req.getRequestDispatcher("ftl/article.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("articleLike".equals(action)) {
            sendArticleLike(resp);
        } else if ("comment".equals(action)) {
            sendComment(req, resp);
        } else if ("commentLike".equals(action)) {
            sendCommentLike(req, resp);
        } else if ("favoured".equals(action)) {
            sendArticleFavoured(resp);
        }
    }

    private void sendArticleFavoured(HttpServletResponse resp) throws IOException{
        Favourite favourite = new Favourite(userId, articleId);

        boolean isArticleFavoured = favouriteDao.isFavoured(favourite);

        if (isArticleFavoured) {
            favouriteDao.delete(favourite);
        } else {
            favouriteDao.insert(favourite);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Создайте объект JSON для отправки
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("isArticleFavoured", !isArticleFavoured);

        resp.getWriter().print(responseJSON);
    }


    private void sendArticleLike(HttpServletResponse resp) throws IOException {
        Like like = new Like(userId, articleId, true);

        boolean isArticleLiked = likeDao.isLiked(like);

        if (isArticleLiked) {
            likeDao.delete(like);
        } else {
            likeDao.insert(like);
        }

        int count = likeDao.getLikesCount(articleId, true);
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


        int commentId = commentDao.findCommentId(commentText, currentTime);

        CommentDto commentDto = commentService.getById(commentId);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(commentDto);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.close();
    }



    private void sendCommentLike(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int commentId = Integer.parseInt(req.getParameter("commentId"));

        LikeDaoImpl likeDao = new LikeDaoImpl();
        Like like = new Like(userId, commentId, false);
        Comment comment = commentDao.getById(commentId);

        boolean isCommentLiked = likeDao.isLiked(like);

        if (isCommentLiked) {
            likeDao.delete(like);
        } else {
            likeDao.insert(like);
        }

        int count = likeDao.getLikesCount(commentId, false);
        comment.setLikes(count);
        commentDao.update(comment);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Создайте объект JSON для отправки
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("likesCount", count);
        responseJSON.put("isCommentLiked", !isCommentLiked);

        resp.getWriter().print(responseJSON);
    }
}
