package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.dto.UserDto;
import ru.kpfu.itis.asadullin.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userInfoServlet", urlPatterns = "/user")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        UserDto userDto = new UserServiceImpl().getById(userId);

        req.setAttribute("user", userDto);

        req.getRequestDispatcher("ftl/user_info.ftl").forward(req, resp);
    }
}
