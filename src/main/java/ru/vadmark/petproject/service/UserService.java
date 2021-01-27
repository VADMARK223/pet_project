package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.config.property.ProjectProperties;
import ru.vadmark.petproject.entity.RoleEntity;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.RoleEntityRepository;
import ru.vadmark.petproject.repository.UserEntityRepository;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.repository.model.UserForm;

import java.util.Base64;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    public static final String ROLE_USER = "ROLE_USER";

    private final UserEntityRepository userRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final ProjectProperties properties;

    public boolean saveUser(UserForm userForm) {
        if (userRepository.findByUsername(userForm.getUsername()).isPresent()) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(userForm.getUsername());

        RoleEntity roleEntity = roleEntityRepository.findByName(ROLE_USER);
        if (roleEntity == null) {
            log.error("Role '{}' not exists.", ROLE_USER);
            throw new RuntimeException("Role '" + ROLE_USER + "' not found.");
        }
        user.getRoles().add(roleEntity);
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userRepository.save(user);
        log.info("Save user.");

        if (properties.getAutologin()) {
            userDetailsService.authenticationUser(user.getUsername());
        }

        return true;
    }

    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public String registrationUser(RegistrationForm registrationForm) {
        if (registrationForm.getUsername() == null) {
            return "Username must not be empty.";
        }

        if (registrationForm.getPassword() == null) {
            return "Password must not be empty.";
        }

        if (registrationForm.getConfirmPassword() == null) {
            return "Confirm password must not be empty.";
        }

        if (registrationForm.getUsername().length() < properties.getMinUsernameLength()) {
            return String.format("Username is too short (minimum is %d characters).", properties.getMinUsernameLength());
        }

        if (registrationForm.getPassword().length() < properties.getMinPasswordLength()) {
            return String.format("Minimum length password is %d characters.", properties.getMinPasswordLength());
        }

        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            return "Password confirmation doesn't match Password.";
        }

        if (!this.saveUser(registrationForm)) {
            return "Username is already taken.";
        }

        return null;
    }

    public String getAvatarAsBase64ByUsername(String username) {
        String result = null;
        byte[] rawAvatar;
        UserEntity userEntity = this.getUserByUsername(username);
        if (userEntity != null) {
            rawAvatar = userEntity.getAvatar();
            String imgAsBase64 = rawAvatar == null ? "" : new String(Base64.getEncoder().encode(rawAvatar));
            result = "data:image/jpeg;base64," + imgAsBase64;
        }
        return result;
    }
}
