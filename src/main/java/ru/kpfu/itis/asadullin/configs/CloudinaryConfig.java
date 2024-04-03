package ru.kpfu.itis.asadullin.configs;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.asadullin.util.CloudinaryUtil;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return CloudinaryUtil.getInstance();
    }
}
