package com.technohacks.bank.model;

public class BankAccount {
	
    private String accountNumber;
    private String accountHolder;
    private String pin;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format(
            "\nAccount Details:\n-----------------\nAccount Number : %s\nAccount Holder : %s\nBalance        : $%.2f",
            accountNumber, accountHolder, balance
        );
    }
}
