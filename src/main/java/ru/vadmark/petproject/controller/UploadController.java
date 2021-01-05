package ru.vadmark.petproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.model.AvatarForm;
import ru.vadmark.petproject.service.UserService;

import java.util.Base64;


/**
 * Author: Markitanov Vadim
 * Date: 31.12.2020
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class UploadController {

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // 1 MB 1*1024*1024
        commonsMultipartResolver.setMaxUploadSize(Long.MAX_VALUE);
        return commonsMultipartResolver;
    }


    private final UserService userService;

    @GetMapping("/upload")
    public String upload(Model model) {
        byte[] rawAvatar = null;

        UserEntity userEntity = userService.getPrincipalUserEntity();
        if (userEntity != null) {
            rawAvatar = userEntity.getAvatar();
        }

        String imgAsBase64 = rawAvatar == null ? "" : new String(Base64.getEncoder().encode(rawAvatar));
        model.addAttribute("imgAsBase64", "data:image/jpeg;base64," + imgAsBase64);

        model.addAttribute("avatarForm", new AvatarForm());

        return "upload";
    }

    @PostMapping("/upload")
    public String postUpload(Model model, @ModelAttribute("avatarForm") AvatarForm avatarForm) {
        log.info("avatarForm: {}.", avatarForm);

        byte[] rawAvatar = null;
        UserEntity userEntity = userService.getPrincipalUserEntity();
        if (userEntity != null) {
            userEntity.setAvatar(avatarForm.getImage().getBytes());
            userService.saveUser(userEntity);

            rawAvatar = userEntity.getAvatar();
        }
        String imgAsBase64 = rawAvatar == null ? "" : new String(Base64.getEncoder().encode(rawAvatar));
        model.addAttribute("imgAsBase64", "data:image/jpeg;base64," + imgAsBase64);
        model.addAttribute("avatarForm", avatarForm);
        return "upload";
    }
}
