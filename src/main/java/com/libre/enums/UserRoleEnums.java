package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoleEnums {
    SUPER_ADMIN(1L, "超级管理员"),
    ADMIN(2L, "管理员"),
    READER(3L, "读者");

    private final Long id;
    private final String roleName;
}
