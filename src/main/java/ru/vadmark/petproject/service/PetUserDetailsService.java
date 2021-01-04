package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.config.jwt.JwtProvider;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PetUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public PetUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username: {}.", username);

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        String token = jwtProvider.generateToken(user.getUsername());
        log.info("Generate token: '{}'.", token);

        return PetUserDetails.fromUserEntity(user);
    }
}
