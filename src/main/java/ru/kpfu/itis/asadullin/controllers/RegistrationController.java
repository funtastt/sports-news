package ru.kpfu.itis.asadullin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.asadullin.model.entity.User;
import ru.kpfu.itis.asadullin.model.service.UserService;

import java.sql.Date;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegistrationForm(Model model) {
        model.addAttribute("isLoggedIn", false);
        return "registration";
    }

    @PostMapping
    public String registerUser(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            Date dateOfBirth,
            String gender,
            String country,
            String city,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        User user = new User(username, email, password, firstName, lastName, dateOfBirth, country, city, gender.equalsIgnoreCase("male"), "");

        boolean ifUserExists = userService.ifUserExists(user);

        if (!ifUserExists) {
            userService.insert(user);
            return "redirect:/login";
        } else {
            model.addAttribute("isLoggedIn", false);
            redirectAttributes.addFlashAttribute("error", "User already exists");
            return "redirect:/registration";
        }
    }
}
