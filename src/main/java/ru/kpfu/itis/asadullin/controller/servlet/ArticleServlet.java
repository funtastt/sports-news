package ru.kpfu.itis.asadullin.controller.servlet;

import com.google.gson.Gson;
import org.cloudinary.json.JSONObject;
import ru.kpfu.itis.asadullin.model.dao.impl.FavouriteDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.LikeDaoImpl;
import ru.kpfu.itis.asadullin.model.dto.ArticleDto;
import ru.kpfu.itis.asadullin.model.dto.CommentDto;
import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.model.dao.impl.ArticleDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.CommentDaoImpl;
import ru.kpfu.itis.asadullin.model.entity.Favourite;
import ru.kpfu.itis.asadullin.model.entity.Like;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;
import ru.kpfu.itis.asadullin.model.service.impl.CommentServiceImpl;

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

// TODO: для нового пользователя лайкнутые комментарии все равно красные
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
        commentService = new CommentServiceImpl(userId);
        ArticleServiceImpl service = new ArticleServiceImpl();

        currentArticle = articleDao.getByTitle(title);
        currentArticle.setViews(currentArticle.getViews() + 1);
        articleDao.update(currentArticle);
        ArticleDto articleDto = service.getById(currentArticle.getArticleId());


        articleId = articleDao.findArticleId(currentArticle.getContent());
        userId = findUserIdInCookie(req);

        LikeDaoImpl likeDao = new LikeDaoImpl();
        boolean isArticleLiked = likeDao.isLiked(new Like(userId,articleId, true));

        FavouriteDaoImpl favouriteDao = new FavouriteDaoImpl();
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
        FavouriteDaoImpl favouriteDao = new FavouriteDaoImpl();
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
        LikeDaoImpl likeDao = new LikeDaoImpl();
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

        CommentDaoImpl commentDao = new CommentDaoImpl();

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
        CommentDaoImpl commentDao = new CommentDaoImpl();
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
