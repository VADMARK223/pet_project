package ru.vadmark.petproject.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
