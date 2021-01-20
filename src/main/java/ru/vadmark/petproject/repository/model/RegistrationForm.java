package ru.vadmark.petproject.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author Markitanov Vadim
 * @since 16.01.2021
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegistrationForm extends UserForm {
    @NotEmpty
    private String confirmPassword;
}
