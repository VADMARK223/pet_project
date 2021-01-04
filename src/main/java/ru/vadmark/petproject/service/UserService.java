package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.entity.RoleEntity;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.model.UserForm;
import ru.vadmark.petproject.repository.RoleEntityRepository;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    public static final String ROLE_USER = "ROLE_USER";
    private final UserEntityRepository userRepository;
    private final RoleEntityRepository roleEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username: {}.", username);

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return PetUserDetails.fromUserEntity(user);
    }

    public boolean saveUser(UserForm userForm) {
        if (userRepository.findByUsername(userForm.getName()) != null) {
            return false;
        }

        UserEntity user = new UserEntity();
        user.setUsername(userForm.getName());

        RoleEntity roleEntity = roleEntityRepository.findByName(ROLE_USER);
        if (roleEntity == null) {
            log.error("Role '{}' not exists.", ROLE_USER);
            throw new RuntimeException("Role '" + ROLE_USER + "' not found.");
        }
        user.getRoles().add(roleEntity);
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userRepository.save(user);
        log.info("Save user.");

        // Auto login
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                PetUserDetails.fromUserEntity(user).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
