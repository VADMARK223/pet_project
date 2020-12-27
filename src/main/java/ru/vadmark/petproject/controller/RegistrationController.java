package ru.vadmark.petproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.vadmark.petproject.model.UserForm;
import ru.vadmark.petproject.service.UserService;

import javax.validation.Valid;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
    @Value("${registration.username.length: 2}")
    private short usernameLength;

    @Value("${registration.password.length: 8}")
    private short passwordLength;

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", createCustomUser());
        return "registration";
    }

    private UserForm createCustomUser() {
        UserForm result = new UserForm();
        result.setName("User#" + getRandomInt(1, 1000));
        result.setPassword(String.valueOf(getRandomInt(1, 1000)));
//        result.setConfirmPassword(String.valueOf(getRandomInt(1, 1000)));
        result.setConfirmPassword(result.getPassword());
        return result;
    }

    @SuppressWarnings("SameParameterValue")
    private int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult, Model model) {
        log.info("User form: {}.", userForm);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Error");
        }

        if (userForm.getName().length() < usernameLength) {
            model.addAttribute("error", String.format("Username is too short (minimum is %d characters).", usernameLength));
            return "registration";
        }

        if (userForm.getPassword().length() < passwordLength) {
            model.addAttribute("error", String.format("Minimum length password is %d characters.", passwordLength));
            return "registration";
        }

        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            model.addAttribute("error", "Password confirmation doesn't match Password.");
            return "registration";
        }

        if (!userService.saveUser(userForm)) {
            model.addAttribute("error", "Username is already taken.");
            return "registration";
        }

        return "redirect:/";
    }
}
