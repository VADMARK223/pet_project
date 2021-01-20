package ru.vadmark.petproject.controller.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.repository.model.UserForm;
import ru.vadmark.petproject.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Author: Markitanov Vadim
 * Date: 07.01.2021
 */
@Api(tags = "Svelte")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("${rest.svelte.base.path}")
public class SvelteController {
    private final UserEntityRepository userRepository;
    private final UserService userService;

    @GetMapping("/admin")
    public List<UserEntity> admin() {
        log.info("Get admin");
        return userRepository.findAll();
    }

    @DeleteMapping("/admin/user/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Delete user: {}.", id);
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(userRepository::delete);
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody @Valid UserForm userForm) {
        log.info("data: {}.", userForm);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(999L);
        userEntity.setUsername(userForm.getUsername());
        userEntity.setPassword(userForm.getPassword());
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserEntity> registration(@RequestBody RegistrationForm registrationForm) throws MethodArgumentNotValidException, NoSuchMethodException {
        log.info("Registration form: {}.", registrationForm);

        String error = userService.registrationUser(registrationForm);
        if (error != null) {
            final MethodParameter methodParameter = new MethodParameter(RegistrationForm.class.getMethod("getConfirmPassword"), -1);
            final BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(registrationForm, "registrationForm");
            ObjectError objectError = new ObjectError("registrationForm", error);
            bindingResult.addError(objectError);
            throw new MethodArgumentNotValidException(methodParameter, bindingResult);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(999L);
        userEntity.setUsername(registrationForm.getUsername());
        userEntity.setPassword(registrationForm.getPassword());
        return ResponseEntity.ok(userEntity);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (e.getTarget() instanceof RegistrationForm) {
            BindingResult bindingResult = e.getBindingResult();
            if (!bindingResult.getAllErrors().isEmpty()) {
                return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Invalid Username or password.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
