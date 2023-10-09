package ru.kpfu.itis.asadullin.controller.servlet;

import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.service.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

// TODO: Выводите на экран push-уведомление, если какие то поля не заполнены
// TODO: Message: org.postgresql.util.PSQLException: Не указано значение для параметра 13.
@WebServlet(name = "registrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("ftl/registration.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String firstName = req.getParameter("first_name");
        if (firstName == null || firstName.isEmpty()) {
            firstName = "Unknown";
        }

        String lastName = req.getParameter("last_name");
        if (lastName == null || lastName.isEmpty()) {
            lastName = "Unknown";
        }

        String dateOfBirthString = req.getParameter("date_of_birth");
        Date dateOfBirth = (dateOfBirthString == null || dateOfBirthString.isEmpty())
                ? new Date(System.currentTimeMillis())
                : Date.valueOf(dateOfBirthString);

        String gender = req.getParameter("gender");
        if (gender == null || gender.isEmpty()) {
            gender = "male";
        }

        String country = req.getParameter("country");
        if (country == null || country.isEmpty()) {
            country = "Unknown";
        }

        String city = req.getParameter("city");
        if (city == null || city.isEmpty()) {
            city = "Unknown";
        }

        User user = new User(username, email, password, firstName, lastName, dateOfBirth, country, city, gender.equalsIgnoreCase("male"));

        UserServiceImpl service = new UserServiceImpl();

        service.insert(user);

        resp.sendRedirect("/login");
    }
}
