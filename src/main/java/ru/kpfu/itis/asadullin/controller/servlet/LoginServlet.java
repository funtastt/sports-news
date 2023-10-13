package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.controller.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    resp.sendRedirect("/profile");
                    return;
                }
            }
        }

        req.getRequestDispatcher("ftl/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("remember_me");

        if (username != null & password != null) {
            Dao<User> service = new UserDaoImpl();
            List<User> users = service.getAll();

            boolean isFound = false;
            for (User user : users) {
                if (user.getUsername().equals(username) && PasswordUtil.checkPassword(password, user.getPassword())) {
                    if (rememberMe != null && rememberMe.equals("on")) {
                        Cookie cookie = new Cookie("remember_me", "true");
                        cookie.setMaxAge(-1);
                        resp.addCookie(cookie);
                    }

                    Cookie cookie = new Cookie("user_id", String.valueOf(user.getUserId()));
                    cookie.setMaxAge(-1);
                    resp.addCookie(cookie);

                    HttpSession session = req.getSession();
                    session.setAttribute("user_id", user.getUserId());
                    session.setAttribute("username", user.getUsername());
                    session.setMaxInactiveInterval(-1);

                    resp.sendRedirect("/");
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                resp.sendRedirect("/login");
            }
        } else {
            resp.sendRedirect("/login");
        }
    }
}
