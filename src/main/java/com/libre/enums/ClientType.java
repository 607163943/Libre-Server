package com.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    ADMIN("admin", 1),
    APP("app", 2);
    private final String label;
    private final Integer value;
}
