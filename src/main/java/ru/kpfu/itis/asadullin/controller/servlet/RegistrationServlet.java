package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "registrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isLoggedIn", false);

        req.getRequestDispatcher("ftl/registration.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String dateOfBirthString = req.getParameter("date_of_birth");
        Date dateOfBirth = Date.valueOf(dateOfBirthString);
        String gender = req.getParameter("gender");
        String country = req.getParameter("country");
        String city = req.getParameter("city");

        User user = new User(username, email, password, firstName, lastName, dateOfBirth, country, city, gender.equalsIgnoreCase("male"), "");

        UserServiceImpl service = new UserServiceImpl();

        boolean ifUserExists = service.ifUserExists(user);

        if (!ifUserExists) {
            service.insert(user);
        } else {
            req.setAttribute("isLoggedIn", false);
            resp.getWriter().write(String.valueOf(false));
        }
        resp.sendRedirect("/login");
    }
}
