package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.repository.model.UserForm;
import ru.vadmark.petproject.service.UserService;

import javax.validation.Valid;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Api(tags = "Registration")
@Controller
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", createCustomUser());
        return "registration";
    }

    @ApiOperation("Register a new user.")
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("registrationForm") RegistrationForm registrationForm, Model model) {
        log.info("Registration form: {}.", registrationForm);

        String error = userService.registrationUser(registrationForm);
        if (error != null) {
            model.addAttribute("error", error);
            return "registration";
        }

        return "redirect:/";
    }

    private RegistrationForm createCustomUser() {
        RegistrationForm result = new RegistrationForm();
        result.setUsername("User#" + getRandomInt(1, 1000));
        result.setPassword(String.valueOf(getRandomInt(1, 1000)));
//        result.setConfirmPassword(String.valueOf(getRandomInt(1, 1000)));
        result.setConfirmPassword(result.getPassword());
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    private int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
