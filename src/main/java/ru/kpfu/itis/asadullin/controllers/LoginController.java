package ru.kpfu.itis.asadullin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.asadullin.model.service.UserService;
import ru.kpfu.itis.asadullin.util.PasswordUtil;
import ru.kpfu.itis.asadullin.util.dto.UserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import ru.kpfu.itis.asadullin.util.constants.ServerResources;

@Controller
@RequestMapping(ServerResources.REGISTRATION_URL)
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLoginForm() {
        return ServerResources.LOGIN_PAGE;
    }

    @PostMapping
    public String login(@RequestParam(ServerResources.USERNAME) String username,
                        @RequestParam(ServerResources.PASSWORD) String password,
                        @RequestParam(value = ServerResources.REMEMBER_ME, required = false) String rememberMe,
                        HttpSession session,
                        HttpServletResponse response) {
        List<UserDto> users = userService.getAll();

        for (UserDto user : users) {
            if (user.getUsername().equals(username) && PasswordUtil.checkPassword(password, user.getPassword())) {
                if (rememberMe != null && rememberMe.equals("on")) {
                    Cookie cookie = new Cookie(ServerResources.REMEMBER_ME, "true");
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);
                }

                Cookie cookie = new Cookie(ServerResources.USER_ID, String.valueOf(user.getUserId()));
                cookie.setMaxAge(-1);
                response.addCookie(cookie);

                session.setAttribute(ServerResources.USER_ID, user.getUserId());
                session.setAttribute(ServerResources.USERNAME, user.getUsername());
                session.setMaxInactiveInterval(-1);

                return "redirect:/news";
            }
        }

        return "redirect:/login";
    }
}
