package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author: Markitanov Vadim
 * Date: 28.12.2020
 */
@Api(tags = "Login")
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam(value = "error", required = false) Object error) {
        if (error != null) {
            model.addAttribute("error", "Invalid Username or password.");
        }
        return "login";
    }
}
