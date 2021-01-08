package ru.vadmark.petproject.repository.model;

import lombok.Data;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Author: Markitanov Vadim
 * Date: 06.01.2021
 */
@Data
public class AvatarForm {
    private CommonsMultipartFile image;
}
