package ru.kpfu.itis.asadullin.controllers;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private static final int DIRECTORIES_COUNT = 100;
    private static final String FILE_NAME_PREFIX = "/tmp";
    private final Cloudinary cloudinary;

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService, Cloudinary cloudinary) {
        this.userService = userService;
        this.cloudinary = cloudinary;
    }

    @GetMapping
    public String getProfile(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You are not logged in");
            return "redirect:/login";
        }
        return "profile";
    }

    @PostMapping
    public String updateProfile(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("date_of_birth") Date dateOfBirth,
            @RequestParam("gender") String gender,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("bio") String bio,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You are not logged in");
            return "redirect:/login";
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setMale(gender.equalsIgnoreCase("male"));
        user.setCountry(country);
        user.setCity(city);
        user.setBio(bio);

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String profilePictureUrl = uploadProfilePicture(profilePicture);
            user.setProfilePicture(profilePictureUrl);
        }

        userService.update(user);
        session.setAttribute("user", user);

        return "redirect:/profile";
    }

    private String uploadProfilePicture(MultipartFile profilePicture) throws IOException {
        File file = File.createTempFile(FILE_NAME_PREFIX + File.separator + (filename.hashCode() % DIRECTORIES_COUNT) + File.separator + filename, "");

        try (InputStream content = profilePicture.getInputStream();
             FileOutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[content.available()];
            content.read(buffer);
            out.write(buffer);
        }

        String profilePictureUrl = cloudinary.uploader().upload(file, new HashMap<>()).get("secure_url").toString();
        file.deleteOnExit();

        return profilePictureUrl;
    }
}
