package ru.kpfu.itis.asadullin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.asadullin.model.dao.FriendDao;
import ru.kpfu.itis.asadullin.model.entity.Friend;
import ru.kpfu.itis.asadullin.model.service.UserService;
import ru.kpfu.itis.asadullin.util.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
@RequestMapping("/user")
public class UserInfoController {
    private final FriendDao friendDao;
    private final UserService userService;
    
    @Autowired
    public UserInfoController(FriendDao friendDao, UserService userService) {
        this.friendDao = friendDao;
        this.userService = userService;
    }

    @GetMapping
    public String getUserInfo(
            HttpServletRequest request,
            @RequestParam("userId") int friendId,
            @ModelAttribute("friend") Friend friend
    ) {
        int currUserId = (int) request.getSession().getAttribute("userId");

        if (currUserId == friendId) {
            return "redirect:/profile";
        }

        UserDto userDto = userService.getById(friendId);
        boolean isFriendAdded = friendDao.isFriendAdded(friend);

        request.setAttribute("user", userDto);
        request.setAttribute("isFriendAdded", isFriendAdded);
        request.setAttribute("isLoggedIn", true);

        return "userInfo";
    }

    @PostMapping
    @ResponseBody
    public String addOrRemoveFriend(@ModelAttribute("friend") Friend friend) {
        boolean isFriendAdded = friendDao.isFriendAdded(friend);

        if (isFriendAdded) {
            friendDao.delete(friend);
        } else {
            friendDao.insert(friend);
        }

        return String.valueOf(!isFriendAdded);
    }
}
