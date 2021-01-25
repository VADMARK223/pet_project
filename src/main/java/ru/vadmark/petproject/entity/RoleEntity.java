package ru.vadmark.petproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Markitanov Vadim
 * Date: 27.12.2020
 */
@ApiModel("Role entity")
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {
    @ApiModelProperty("Role ID.")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty("Role name.")
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
