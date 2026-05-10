package com.mb.ordersystem.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(int requested, int available) {
        super("Insufficient stock. Requested: " + requested + ", available: " + available);
        
    }
}
