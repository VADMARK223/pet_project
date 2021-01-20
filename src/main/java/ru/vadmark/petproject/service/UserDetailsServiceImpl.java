package ru.vadmark.petproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserEntityRepository userRepository;

    @Override
    // @Transactional if roles lazy load.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load user by username.");
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
        }

        return user;
    }
}
