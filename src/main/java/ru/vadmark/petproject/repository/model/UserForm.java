package ru.vadmark.petproject.repository.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Data
public class UserForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
