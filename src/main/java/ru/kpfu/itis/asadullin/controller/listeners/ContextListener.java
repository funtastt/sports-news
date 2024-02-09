package ru.kpfu.itis.asadullin.controller.listeners;

import ru.kpfu.itis.asadullin.model.dao.impl.*;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    ArticleDaoImpl articleDao;
    ArticleServiceImpl articleService;
    FriendDaoImpl friendDao;
    LikeDaoImpl likeDao;
    FavouriteDaoImpl favouriteDao;
    CommentDaoImpl commentDao;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        articleDao = new ArticleDaoImpl();
        articleService = new ArticleServiceImpl(articleDao);
        friendDao = new FriendDaoImpl();
        favouriteDao = new FavouriteDaoImpl();
        likeDao = new LikeDaoImpl();
        commentDao = new CommentDaoImpl();
        sce.getServletContext().setAttribute("articleDao", articleDao);
        sce.getServletContext().setAttribute("likeDao", likeDao);
        sce.getServletContext().setAttribute("friendDao", friendDao);
        sce.getServletContext().setAttribute("favouriteDao", favouriteDao);
        sce.getServletContext().setAttribute("commentDao", commentDao);
        sce.getServletContext().setAttribute("articleService", articleService);
        ServletContextListener.super.contextInitialized(sce);
    }
}
