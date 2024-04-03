package ru.kpfu.itis.asadullin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping
    public void logout(HttpServletResponse response, HttpSession session) throws IOException {
        clearResources(response, session);
    }

    @PostMapping
    public void logoutPost(HttpServletResponse response, HttpSession session) throws IOException {
        clearResources(response, session);
    }

    private void clearResources(HttpServletResponse response, HttpSession session) throws IOException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("/login");
    }
}
