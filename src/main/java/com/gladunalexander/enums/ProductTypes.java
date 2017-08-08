package com.gladunalexander.enums;

/**
 * @author Alexander Gladun
 */
public enum ProductTypes {

    NOTEBOOK(1, "NOTEBOOK"),
    PHONE(2, "PHONE"),
    TABLET(3, "TABLET"),
    TV(4, "TV");

    private final int id;

    private final String value;

    ProductTypes(int id, String value) {
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
