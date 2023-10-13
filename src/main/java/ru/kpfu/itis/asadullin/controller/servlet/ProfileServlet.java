package ru.kpfu.itis.asadullin.controller.servlet;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashMap;

import static ru.kpfu.itis.asadullin.controller.util.CloudinaryUtil.getCloudinary;

// TODO: Выводите на экран push-уведомление, если какие то поля не заполнены
@WebServlet(name = "profileServlet", urlPatterns = "/profile")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 2 * 5 * 1024 * 1024
)
public class ProfileServlet extends HttpServlet {
    private int userId;
    private static final int DIRECTORIES_COUNT = 100;
    private static final String FILE_NAME_PREFIX = "/tmp";
    private final Cloudinary cloudinary = getCloudinary();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        findUserIdInCookie(req);

        User user = new UserDaoImpl().getById(userId);
        req.setAttribute("user", user);

        req.getRequestDispatcher("ftl/profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        updateUserMainInfo(req);
        updatePassword(req);
        updateProfilePicture(req, resp);
}

    private void updateUserMainInfo(HttpServletRequest req) {
        String username = req.getParameter("username");

        if (username == null || username.isEmpty()) {
            return;
        }

        String email = req.getParameter("email");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        Date dateOfBirth = Date.valueOf(req.getParameter("date_of_birth"));
        String gender = req.getParameter("gender");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String bio = req.getParameter("bio");

        User user = new User(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setMale(gender.equalsIgnoreCase("male"));
        user.setCountry(country);
        user.setCity(city);
        user.setBio(bio);

        UserServiceImpl service = new UserServiceImpl();

        service.update(user);
    }

    private void updatePassword(HttpServletRequest req) {
        String password = req.getParameter("password");

        if (password == null || password.isEmpty()) {
            return;
        }

        User user = new User(userId);
        user.setPassword(password);
        UserServiceImpl service = new UserServiceImpl();
        service.update(user);
    }

    private void updateProfilePicture(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Part profilePicture = req.getPart("profilePicture");
            String filename = Paths.get(profilePicture.getSubmittedFileName()).getFileName().toString();

            File file = File.createTempFile(FILE_NAME_PREFIX + File.separator + (filename.hashCode() % DIRECTORIES_COUNT) + File.separator + filename, "");

            InputStream content = profilePicture.getInputStream();
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[content.available()];
            content.read(buffer);
            out.write(buffer);
            file.deleteOnExit();
            out.close();

            String profilePictureUrl = cloudinary.uploader().upload(file, new HashMap<>()).get("secure_url").toString();
            System.out.println(profilePictureUrl);

            User user = new User(userId);
            user.setProfilePicture(profilePictureUrl);
            UserServiceImpl service = new UserServiceImpl();
            service.update(user);

            resp.setContentType("text/plain");
            resp.getWriter().write(profilePictureUrl);
        } catch (Exception ignored) {

        }
    }

    private void findUserIdInCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    userId = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }
    }
}
