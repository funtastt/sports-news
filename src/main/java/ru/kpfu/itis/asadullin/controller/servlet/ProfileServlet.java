package ru.kpfu.itis.asadullin.controller.servlet;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.dao.impl.UserDaoImpl;
import ru.kpfu.itis.asadullin.model.service.impl.ArticleServiceImpl;
import ru.kpfu.itis.asadullin.model.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashMap;

import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.findUserIdInCookie;
import static ru.kpfu.itis.asadullin.controller.servlet.AllNewsServlet.isLoggedIn;
import static ru.kpfu.itis.asadullin.controller.util.CloudinaryUtil.getCloudinary;

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

    UserDaoImpl userDao;
    UserServiceImpl userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userDao = (UserDaoImpl) config.getServletContext().getAttribute("userDao");
        userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userId = findUserIdInCookie(req);

        boolean isLogged = isLoggedIn(req);

        if (isLogged) {
            User user = userDao.getById(userId);
            req.setAttribute("user", user);
            req.setAttribute("isLoggedIn", true);
            req.getRequestDispatcher("ftl/profile.ftl").forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
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


        userService.update(user);
    }

    private void updatePassword(HttpServletRequest req) {
        String password = req.getParameter("password");

        if (password == null || password.isEmpty()) {
            return;
        }

        User user = new User(userId);
        user.setPassword(password);
        userService.update(user);
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

            User user = new User(userId);
            user.setProfilePicture(profilePictureUrl);
            userService.update(user);

            resp.setContentType("text/plain");
            resp.getWriter().write(profilePictureUrl);
        } catch (Exception ignored) {

        }
    }
}
