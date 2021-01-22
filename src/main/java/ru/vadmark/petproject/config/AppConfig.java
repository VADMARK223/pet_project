package ru.vadmark.petproject.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.vadmark.petproject.config.property.ProjectProperties;

/**
 * @author Markitanov Vadim
 * @since 22.01.2021
 */
@EnableConfigurationProperties(ProjectProperties.class)
@Configuration
public class AppConfig {
    public AppConfig() {
//        Lang.setLocale("lang", "ru", StandardCharsets.UTF_8.name());
    }
}
