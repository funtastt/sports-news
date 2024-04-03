package ru.kpfu.itis.asadullin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.asadullin.util.constants.ServerResources;

@Controller
@RequestMapping(ServerResources.ABOUT_US_PAGE)
public class AboutUsController {

    @GetMapping()
    public String showAboutPage() {
        return ServerResources.ABOUT_US_PAGE;
    }
}