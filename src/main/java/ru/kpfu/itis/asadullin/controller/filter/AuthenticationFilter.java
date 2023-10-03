package ru.kpfu.itis.asadullin.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "authenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
        boolean isRemembered = false;
        boolean isUserIdEmpty = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember_me")) {
                    isRemembered = true;
                }
                if (cookie.getName().equals("user_id")) {
                    isUserIdEmpty = true;
                }
            }
        }
        if (!isUserIdEmpty & !isRemembered & session == null & !uri.contains("login") & !uri.contains("registration")) {
            ((HttpServletResponse)servletResponse).sendRedirect("/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
