package com.gladunalexander.enums;

/**
 * @author Alexander Gladun
 */
public enum RolesEnum {

    USER(1, "ROLE_USER"),
    MANAGER(2, "ROLE_MANAGER"),
    ADMIN(3, "ROLE_ADMIN");

    private final int id;

    private final String value;

    RolesEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
