package ru.kpfu.itis.asadullin.controller.handler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ExceptionHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isLoggedIn", false);
        handleException(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isLoggedIn", false);
        handleException(req, resp);
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable;
        if (req.getAttribute("javax.servlet.error.exception") != null) {
            throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        } else {
            throwable = null;
        }

        Integer code = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String uri = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        req.setAttribute("statusCode", code);
        req.setAttribute("uri", uri == null ? "" : uri);
        if (throwable != null) {
            req.setAttribute("message", throwable.getMessage());
        }

        if (code == 404) {
            req.getRequestDispatcher("ftl/404.ftl").forward(req, resp);
        } else {
            req.getRequestDispatcher("ftl/error.ftl").forward(req, resp);
        }
    }
}
