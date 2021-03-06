package ru.vadmark.petproject.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Markitanov Vadim
 * @since 04.01.2021
 */
@Api(tags = "Admin")
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@Controller
public class AdminController {
    private final UserEntityRepository userRepository;

    @ApiOperation(notes = "Find all user in DB and return admin page.", value = "Get users list.")
    @GetMapping()
    public ModelAndView admin() {
        return createAdminModelAndView(userRepository.findAll());
    }

    private ModelAndView createAdminModelAndView(List<UserEntity> users) {
        final ModelAndView result = new ModelAndView("admin");
        result.addObject("users", users);
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        result.addObject("username", userEntity.getUsername());
        return result;
    }

    @ApiOperation("Get user by id.")
    @GetMapping("/user/{id}")
    public ModelAndView getUser(@PathVariable long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        List<UserEntity> users = new ArrayList<>();
        optionalUser.ifPresent(users::add);
        return createAdminModelAndView(users);
    }

    @ApiOperation("Delete user by id.")
    @PostMapping("/user/{id}")
    public ModelAndView deleteUser(@PathVariable long id) {
        log.info("Delete user: {}.", id);
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
        return admin();
    }
}
