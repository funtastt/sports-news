package ru.kpfu.itis.asadullin.controller.servlet;

import com.google.gson.Gson;
import ru.kpfu.itis.asadullin.model.dao.impl.FriendDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.model.dto.UserDto;
import ru.kpfu.itis.asadullin.model.entity.Friend;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.findUserIdInCookie;

@WebServlet(name = "findFriendsServlet", urlPatterns = "/find_friends")
public class FindFriendsServlet extends HttpServlet {
    // TODO: не всегда хорошо работает кнопка "Добавить в друзья"

    private List<UserDto> allUsers;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserServiceImpl userService = new UserServiceImpl();
        FriendDaoImpl friendDao = new FriendDaoImpl();
        int currentUserId = findUserIdInCookie(req);
        allUsers = userService.getAll();
        UserDto currentUser = userService.getById(currentUserId);

        for (UserDto user : allUsers) {
            user.setAdded(friendDao.isFriendAdded(new Friend(currentUserId, user.getUserId())));
        }

        if (currentUser != null) {
            allUsers.remove(currentUser);
        }

        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("ftl/findFriends.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lambda = req.getParameter("searchText");

        List<UserDto> matchedUsers = new ArrayList<>();

        for (UserDto user : allUsers) {
            if (user.getUsername().toLowerCase().contains(lambda.toLowerCase())) {
                matchedUsers.add(user);
            }
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(matchedUsers);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.close();
    }
}
