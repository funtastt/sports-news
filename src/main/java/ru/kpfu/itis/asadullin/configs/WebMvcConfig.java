package ru.kpfu.itis.asadullin.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kpfu.itis.asadullin.util.constants.ServerResources;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName(ServerResources.LOGIN_PAGE);
        registry.addViewController("/about_us").setViewName(ServerResources.ABOUT_US_PAGE);
    }
}
