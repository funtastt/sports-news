package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.dao.impl.FriendDaoImpl;
import ru.kpfu.itis.asadullin.controller.util.dto.UserDto;
import ru.kpfu.itis.asadullin.model.entity.Friend;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;
import ru.kpfu.itis.asadullin.model.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.findUserIdInCookie;
import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.isLoggedIn;

@WebServlet(name = "userInfoServlet", urlPatterns = "/user")
public class UserInfoServlet extends HttpServlet {
    FriendDaoImpl friendDao;
    UserServiceImpl userService;
    int currUserId, friendId;

    Friend friend;

    @Override
    public void init(ServletConfig config) throws ServletException {
        friendDao = (FriendDaoImpl) config.getServletContext().getAttribute("friendDao");
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        currUserId = findUserIdInCookie(req);
        friendId = Integer.parseInt(req.getParameter("userId"));
        if (currUserId == friendId) {
            resp.sendRedirect("/profile");
            return;
        }

        friend = new Friend(currUserId, friendId, new Timestamp(System.currentTimeMillis()));

        UserDto userDto = userService.getById(friendId);
        boolean isFriendAdded = friendDao.isFriendAdded(friend);

        req.setAttribute("user", userDto);
        req.setAttribute("isFriendAdded", isFriendAdded);
        req.setAttribute("isLoggedIn", isLoggedIn(req));

        req.getRequestDispatcher("ftl/userInfo.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (friend == null) {
            currUserId = findUserIdInCookie(req);
            friendId = Integer.parseInt(req.getParameter("friendId"));
            friend = new Friend(currUserId, friendId, new Timestamp(System.currentTimeMillis()));
        }

        boolean isFriendAdded = friendDao.isFriendAdded(friend);

        if (isFriendAdded) {
            friendDao.delete(friend);
        } else {
            friendDao.insert(friend);
        }

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(String.valueOf(!isFriendAdded));
    }
}
