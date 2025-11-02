package com.technohacks.bank.exception;

public class InsufficientBalanceException extends Exception {
    private double shortfall;

    public InsufficientBalanceException(String message, double shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public double getShortfall() {
        return shortfall;
    }
}
