package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.dto.UserDto;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.service.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.service.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

// TODO: Выводите на экран push-уведомление, если какие то поля не заполнены
@WebServlet(name = "profileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    private int userId;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        findUserIdInCookie(req);

        User user = new UserDaoImpl().getById(userId);
        req.setAttribute("user", user);

        req.getRequestDispatcher("ftl/profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username != null && !username.isEmpty()) {
            updateUserMainInfo(req);
        }

        if (password != null && !password.isEmpty()) {
            User user = new User(userId);
            user.setPassword(password);
            UserServiceImpl service = new UserServiceImpl();
            service.update(user);
        }
    }

    private void updateUserMainInfo(HttpServletRequest req) {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Date dateOfBirth = Date.valueOf(req.getParameter("date_of_birth"));
        String gender = req.getParameter("gender");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String bio = req.getParameter("bio");

        User user = new User(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setMale(gender.equalsIgnoreCase("male"));
        user.setCountry(country);
        user.setCity(city);
        user.setBio(bio);

        UserServiceImpl service = new UserServiceImpl();

        service.update(user);
    }

    private void findUserIdInCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    userId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
    }
}
