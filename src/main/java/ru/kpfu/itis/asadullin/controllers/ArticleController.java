package ru.kpfu.itis.asadullin.controllers;

import com.google.gson.Gson;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.asadullin.model.entity.Article;
import ru.kpfu.itis.asadullin.model.entity.Comment;
import ru.kpfu.itis.asadullin.model.entity.Favourite;
import ru.kpfu.itis.asadullin.model.entity.Like;
import ru.kpfu.itis.asadullin.model.service.ArticleService;
import ru.kpfu.itis.asadullin.model.service.CommentService;
import ru.kpfu.itis.asadullin.model.service.FavouriteService;
import ru.kpfu.itis.asadullin.model.service.LikeService;
import ru.kpfu.itis.asadullin.util.dto.ArticleDto;
import ru.kpfu.itis.asadullin.util.dto.CommentDto;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final FavouriteService favouriteService;

    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService, LikeService likeService, FavouriteService favouriteService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.favouriteService = favouriteService;
    }

    @GetMapping
    public String getArticlePage(@RequestParam("articleId") int articleId, Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        ArticleDto articleDto = articleService.getById(articleId);

        boolean isArticleLiked = likeService.isLiked(new Like(userId, articleId, true));

        boolean isArticleFavoured = favouriteService.isFavoured(new Favourite(userId, articleId));

        List<CommentDto> comments = commentService.getAllCommentsForArticle(articleId);
        comments.sort(Comparator.comparing(CommentDto::getSendingTime));

        model.addAttribute("article", articleDto);
        model.addAttribute("comments", comments);
        model.addAttribute("isArticleLiked", isArticleLiked);
        model.addAttribute("isLoggedIn", session.getAttribute("isLoggedIn"));
        model.addAttribute("isArticleFavoured", isArticleFavoured);

        return "ftl/article";
    }

    @PostMapping
    @ResponseBody
    public void performAction(@RequestParam("action") String action, @RequestParam(value = "comment", required = false) String commentText,
                              @RequestParam(value = "commentId", required = false) Integer commentId, HttpSession session, HttpServletResponse response) throws IOException {
        int userId = (int) session.getAttribute("userId");
        int articleId = Integer.parseInt(session.getAttribute("articleId").toString());

        switch (action) {
            case "articleLike":
                toggleArticleLike(userId, articleId, response);
                break;
            case "comment":
                postComment(commentText, userId, articleId, response);
                break;
            case "commentLike":
                toggleCommentLike(userId, commentId, response);
                break;
            case "favoured":
                toggleArticleFavoured(userId, articleId, response);
                break;
            default:
                break;
        }
    }

    private void toggleArticleFavoured(int userId, int articleId, HttpServletResponse response) throws IOException {
        Favourite favourite = new Favourite(userId, articleId);

        favouriteService.toggleFavourite(favourite);

        boolean isArticleFavoured = favouriteService.isFavoured(favourite);

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("isArticleFavoured", !isArticleFavoured);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(responseJSON);
    }

    private void toggleArticleLike(int userId, int articleId, HttpServletResponse response) throws IOException {
        Like like = new Like(userId, articleId, true);

        likeService.toggleLike(like);

        boolean isArticleLiked = likeService.isLiked(like);

        int count = likeService.getLikesCount(articleId, true);

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("likesCount", count);
        responseJSON.put("isArticleLiked", !isArticleLiked);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(responseJSON);
    }

    private void postComment(String commentText, int userId, int articleId, HttpServletResponse response) throws IOException {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Comment comment = new Comment(commentText, currentTime, userId, articleId);
        commentService.insert(comment);

        int commentId = commentService.findCommentId(commentText, currentTime);

        CommentDto commentDto = commentService.getById(commentId);

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(commentDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonResponse);
    }

    private void toggleCommentLike(int userId, int commentId, HttpServletResponse response) throws IOException {
        Like like = new Like(userId, commentId, false);

        likeService.toggleLike(like);

        boolean isCommentLiked = likeService.isLiked(like);

        int count = likeService.getLikesCount(commentId, false);

        commentService.updateLikesCount(commentId, count);

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("likesCount", count);
        responseJSON.put("isCommentLiked", !isCommentLiked);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(responseJSON);
    }
}
