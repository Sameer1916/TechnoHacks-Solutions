package com.technohacks.bank.service;

import com.technohacks.bank.exception.*;
import com.technohacks.bank.model.BankAccount;

import java.util.HashMap;
import java.util.Map;

public class BankAccountService {
    private Map<String, BankAccount> accounts = new HashMap<>();

    public BankAccountService() {
        accounts.put("ACC001", new BankAccount("ACC001", "Shaik", "1234", 5000.00));
        accounts.put("ACC002", new BankAccount("ACC002", "Sameer", "5678", 3000.00));
        accounts.put("ACC003", new BankAccount("ACC003", "technohacks", "9012", 1500.00));
    }

    private BankAccount validate(String accountNumber, String pin)
            throws AccountNotFoundException, UnauthorizedAccessException {
        BankAccount acc = accounts.get(accountNumber);
        if (acc == null) {
            throw new AccountNotFoundException("Account " + accountNumber + " not found.");
        }
        if (!acc.getPin().equals(pin)) {
            throw new UnauthorizedAccessException("PIN is incorrect for account " + accountNumber);
        }
        return acc;
    }

    public double checkBalance(String accountNumber, String pin)
            throws AccountNotFoundException, UnauthorizedAccessException {
        BankAccount acc = validate(accountNumber, pin);
        return acc.getBalance();
    }

    public void withdraw(String accountNumber, String pin, double amount)
            throws AccountNotFoundException, UnauthorizedAccessException, InsufficientBalanceException {
        BankAccount acc = validate(accountNumber, pin);
        if (amount > acc.getBalance()) {
            throw new InsufficientBalanceException("Insufficient funds", amount - acc.getBalance());
        }
        acc.setBalance(acc.getBalance() - amount);
    }

    public void deposit(String accountNumber, String pin, double amount)
            throws AccountNotFoundException, UnauthorizedAccessException {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        BankAccount acc = validate(accountNumber, pin);
        acc.setBalance(acc.getBalance() + amount);
    }

    public void transfer(String fromAccount, String pin, String toAccount, double amount)
            throws AccountNotFoundException, UnauthorizedAccessException, InsufficientBalanceException {
        BankAccount from = validate(fromAccount, pin);
        BankAccount to = accounts.get(toAccount);

        if (to == null) {
            throw new AccountNotFoundException("Destination account " + toAccount + " not found.");
        }

        if (amount > from.getBalance()) {
            throw new InsufficientBalanceException("Insufficient funds for transfer", amount - from.getBalance());
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }
    

    public BankAccount getAccountDetails(String accountNumber, String pin)
            throws AccountNotFoundException, UnauthorizedAccessException {
        return validate(accountNumber, pin);
    }
}
