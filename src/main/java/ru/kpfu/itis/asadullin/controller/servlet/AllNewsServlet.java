package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.service.dao.impl.ArticleDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "newsServlet", urlPatterns = "/news")
public class AllNewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("all_news", new ArticleDaoImpl().getAll());

        req.getRequestDispatcher("news.ftl").forward(req, resp);
    }
}
