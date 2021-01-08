package ru.vadmark.petproject.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@RestController
@RequestMapping("${rest.svelte.base.path}")
public class SvelteController {
    @GetMapping("/test")
    public String test() {
        return "{\"data\":\"vadmark11\"}";
    }

    @GetMapping("/test1")
    public String test1() {
        return "{\"data\":\"vadmark111111\"}";
    }

    @GetMapping("/test3")
    public String test3() {
        return "{\"data\":\"vadmark3333\"}";
    }

    @GetMapping("/test4")
    public String test4() {
        return "{\"data\":\"vadmark444\"}";
    }
}
