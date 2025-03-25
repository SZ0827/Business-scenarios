package com.sz.entity;

import com.sz.ENUM.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "roles")

public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType name;
    public String getAuthority() {
        return name.name();
    }
}
