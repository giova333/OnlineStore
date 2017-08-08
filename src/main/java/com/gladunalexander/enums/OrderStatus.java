package com.gladunalexander.enums;

/**
 * @author Alexander Gladun
 */
public enum OrderStatus {

    BEFORE_PAYMENT(1),
    COMPLITED(2),
    SENT(3),
    ARRIVED(4);

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
