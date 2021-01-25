package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.model.AvatarForm;
import ru.vadmark.petproject.service.UserService;

import java.security.Principal;
import java.util.Base64;


/**
 * Author: Markitanov Vadim
 * Date: 31.12.2020
 */
@Api(tags = "Settings")
@Slf4j
@RequiredArgsConstructor
@Controller
public class SettingsController {
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // 1 MB 1*1024*1024
        commonsMultipartResolver.setMaxUploadSize(Long.MAX_VALUE);

        return commonsMultipartResolver;
    }

    private final UserService userService;

    @CrossOrigin(origins = "*")
    @GetMapping("/settings")
    public ModelAndView settings(Principal principal) {
        byte[] rawAvatar = null;

        UserEntity userEntity = userService.getUserByUsername(principal.getName());
        if (userEntity != null) {
            rawAvatar = userEntity.getAvatar();
        }

        String imgAsBase64 = rawAvatar == null ? "" : new String(Base64.getEncoder().encode(rawAvatar));
        ModelAndView modelAndView = new ModelAndView("settings");
        modelAndView.addObject("imgAsBase64", "data:image/jpeg;base64," + imgAsBase64);
        modelAndView.addObject("avatarForm", new AvatarForm());
        return modelAndView;
    }

    @PostMapping("/upload")
    public ModelAndView upload(@ModelAttribute("avatarForm") AvatarForm avatarForm, Principal principal) {
        log.info("avatarForm: {}.", avatarForm);

        byte[] rawAvatar = null;
        UserEntity userEntity = userService.getUserByUsername(principal.getName());
        if (userEntity != null) {
            userEntity.setAvatar(avatarForm.getImage().getBytes());
            userService.saveUser(userEntity);

            rawAvatar = userEntity.getAvatar();
        }
        String imgAsBase64 = rawAvatar == null ? "" : new String(Base64.getEncoder().encode(rawAvatar));
        ModelAndView modelAndView = new ModelAndView("settings");
        modelAndView.addObject("imgAsBase64", "data:image/jpeg;base64," + imgAsBase64);
        modelAndView.addObject("avatarForm", avatarForm);
        return modelAndView;
    }
}
