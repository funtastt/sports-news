package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.User;
import ru.kpfu.itis.asadullin.service.dao.UserService;
import ru.kpfu.itis.asadullin.service.dao.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

// TODO: Выводите на экран push-уведомление, если какие то поля не заполнены
@WebServlet(name = "profileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = 0;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    userId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Date dateOfBirth = Date.valueOf(req.getParameter("date_of_birth"));
        String profilePicture = ""; // TODO:
        boolean isVerified = false; // TODO:
        String gender = req.getParameter("gender");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String bio = req.getParameter("bio");

        User user = new User(userId, username, email, password, firstName, lastName, dateOfBirth, country, city, profilePicture, bio, isVerified, gender.equals("male"));

        UserService service = new UserServiceImpl();

        service.update(user);

        resp.sendRedirect("/");
    }
}
