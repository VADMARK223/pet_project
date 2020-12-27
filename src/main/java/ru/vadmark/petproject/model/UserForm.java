package ru.vadmark.petproject.model;

import lombok.Data;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Data
public class UserForm {
    private String name;
    private String password;
    private String confirmPassword;
}
