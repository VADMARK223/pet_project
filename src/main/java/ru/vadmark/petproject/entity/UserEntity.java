package ru.vadmark.petproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Markitanov Vadim
 * Date: 04.01.2021
 */
@ApiModel("User entity")
@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @ApiModelProperty("Role ID.")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;

    @ToString.Exclude
    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @ToString.Exclude
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @ToString.Exclude
    @Column(name = "avatar")
    private byte[] avatar;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        this.getRoles().forEach(roleEntity -> grantedAuthorities.add(new SimpleGrantedAuthority(roleEntity.getName())));
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
