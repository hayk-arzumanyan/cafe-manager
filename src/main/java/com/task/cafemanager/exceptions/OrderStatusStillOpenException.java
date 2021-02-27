package com.task.cafemanager.exceptions;

public class OrderStatusStillOpenException extends RuntimeException {
    public OrderStatusStillOpenException(String name) {
        super(name);
    }
}
