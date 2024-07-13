package com.dreamwheels.dreamwheels.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),

    SUPER_ADMIN_CREATE("super-admin:create"),
    SUPER_ADMIN_DELETE("super-admin:delete"),
    SUPER_ADMIN_UPDATE("super-admin:update"),
    SUPER_ADMIN_READ("super-admin:read");

    private final String permission;
}