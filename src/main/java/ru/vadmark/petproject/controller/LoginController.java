package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

/**
 * Author: Markitanov Vadim
 * Date: 28.12.2020
 */
@Api(tags = "Login")
@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login/failure")
    public ModelAndView failure(@RequestParam String error) {
        return new ModelAndView("login", Collections.singletonMap("error", error));
    }
}
