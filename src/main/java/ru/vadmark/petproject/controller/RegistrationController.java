package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.service.UserService;

import java.util.Collections;

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
    public ModelAndView registration() {
        return new ModelAndView("registration", Collections.singletonMap("registrationForm", createCustomUser()));
    }

    @ApiOperation("Register a new user.")
    @PostMapping("/registration")
    public ModelAndView addUser(@ModelAttribute("registrationForm") RegistrationForm registrationForm) {
        log.info("Registration form: {}.", registrationForm);

        String error = userService.registrationUser(registrationForm);
        if (error != null) {
            return new ModelAndView("registration", Collections.singletonMap("error", error));
        }

        return new ModelAndView("index");
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
