package ru.kpfu.itis.asadullin.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.asadullin.util.constants.ServerResources;

@Configuration
@ComponentScan(basePackages = {ServerResources.BASE_PACKAGE})
public class CoreConfig {
}