package ru.vadmark.petproject.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Markitanov Vadim
 * @since 22.01.2021
 */
@Data
@ConfigurationProperties(prefix = "pet-project")
public class ProjectProperties {
    private Boolean autologin;
    private Integer minUsernameLength;
    private Integer minPasswordLength;
    private String clientUrl;
    private String clientContextPath;
}
