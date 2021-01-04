package ru.vadmark.petproject.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.vadmark.petproject.entity.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
public class PetUserDetails implements UserDetails {
    private String username;
    private String password;
    private final Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

    public static PetUserDetails fromUserEntity(UserEntity userEntity) {
        PetUserDetails petUserDetails = new PetUserDetails();
        petUserDetails.username = userEntity.getUsername();
        petUserDetails.password = userEntity.getPassword();
        userEntity.getRoles().forEach(roleEntity -> petUserDetails.grantedAuthorities.add(new SimpleGrantedAuthority(roleEntity.getName())));
        return petUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
