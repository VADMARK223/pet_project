package ru.vadmark.petproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RestController
public class MainController {
    @GetMapping("/test")
    public String test() {
        return "{\"data\":\"vadmark\"}";
    }
}
