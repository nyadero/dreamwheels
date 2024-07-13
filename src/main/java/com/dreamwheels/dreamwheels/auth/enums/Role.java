package com.dreamwheels.dreamwheels.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dreamwheels.dreamwheels.auth.enums.Permission.*;

@Getter
@AllArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE, MANAGER_READ, MANAGER_CREATE, MANAGER_UPDATE, MANAGER_DELETE)),
    MANAGER(Set.of(MANAGER_READ, MANAGER_CREATE, MANAGER_UPDATE, MANAGER_DELETE)),
    SUPER_ADMIN(Set.of(SUPER_ADMIN_CREATE, SUPER_ADMIN_UPDATE, SUPER_ADMIN_DELETE, SUPER_ADMIN_READ, ADMIN_READ,
            ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE, MANAGER_READ, MANAGER_CREATE, MANAGER_UPDATE, MANAGER_DELETE )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE " + this.name()));
        return authorities;
    }
}
