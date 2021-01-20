package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.entity.RoleEntity;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.RoleEntityRepository;
import ru.vadmark.petproject.repository.UserEntityRepository;
import ru.vadmark.petproject.repository.model.RegistrationForm;
import ru.vadmark.petproject.repository.model.UserForm;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    public static final String ROLE_USER = "ROLE_USER";

    @Value("${registration.username.length: 2}")
    private short usernameLength;

    @Value("${registration.password.length: 8}")
    private short passwordLength;

    @Value("${registration.autologin: false}")
    private boolean autologin;

    private final UserEntityRepository userRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsServiceImpl petUserDetailsService;

    public boolean saveUser(UserForm userForm) {
        if (userRepository.findByUsername(userForm.getUsername()) != null) {
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

        if (autologin) {
            log.info("Autologin. ");
            UserDetails userDetails = petUserDetailsService.loadUserByUsername(user.getUsername());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        return true;
    }

    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    private UserEntity findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity getUserByUsername(String username) {
        return findUserEntityByUsername(username);
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

        if (registrationForm.getUsername().length() < usernameLength) {
            return String.format("Username is too short (minimum is %d characters).", usernameLength);
        }

        if (registrationForm.getPassword().length() < passwordLength) {
            return String.format("Minimum length password is %d characters.", passwordLength);
        }

        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            return "Password confirmation doesn't match Password.";
        }

        if (!this.saveUser(registrationForm)) {
            return "Username is already taken.";
        }

        return null;
    }
}
