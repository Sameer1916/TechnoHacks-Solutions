package com.technohacks.bank;

import com.technohacks.bank.exception.*;
import com.technohacks.bank.model.BankAccount;
import com.technohacks.bank.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Bank Application Main Class
 * Demonstrates comprehensive exception handling in a banking system
 */
public class BankApplication {
    private static final Logger logger = LoggerFactory.getLogger(BankApplication.class);
    private static BankAccountService bankService = new BankAccountService();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO TECHNOHACKS BANK ACCOUNT SERVICE        ║");
        System.out.println("║         Exception Handling Demonstration             ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");
        
        logger.info("Bank Application started");
        
        // Demonstrate all exception scenarios
        demonstrateAllScenarios();
        
        // Interactive menu
        runInteractiveMode();
        
        logger.info("Bank Application terminated");
    }
    
    private static void demonstrateAllScenarios() {
        System.out.println("\n═══════════════════ DEMONSTRATION MODE ═══════════════════\n");
        
        // Scenario 1: Successful operations
        System.out.println("▶ Scenario 1: Successful Balance Check");
        demonstrateSuccessfulBalanceCheck();
        
        // Scenario 2: Account Not Found Exception
        System.out.println("\n▶ Scenario 2: Account Not Found Exception");
        demonstrateAccountNotFound();
        
        // Scenario 3: Unauthorized Access Exception
        System.out.println("\n▶ Scenario 3: Unauthorized Access Exception");
        demonstrateUnauthorizedAccess();
        
        // Scenario 4: Insufficient Balance Exception
        System.out.println("\n▶ Scenario 4: Insufficient Balance Exception");
        demonstrateInsufficientBalance();
        
        // Scenario 5: Successful withdrawal
        System.out.println("\n▶ Scenario 5: Successful Withdrawal");
        demonstrateSuccessfulWithdrawal();
        
        // Scenario 6: Successful transfer
        System.out.println("\n▶ Scenario 6: Successful Transfer");
        demonstrateSuccessfulTransfer();
        
        System.out.println("\n═══════════════════ END OF DEMONSTRATION ═══════════════════\n");
    }
    
    private static void demonstrateSuccessfulBalanceCheck() {
        try {
            double balance = bankService.checkBalance("ACC001", "1234");
            System.out.println("✓ SUCCESS: Current balance is $" + String.format("%.2f", balance));
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void demonstrateAccountNotFound() {
        try {
            bankService.checkBalance("ACC999", "1234");
        } catch (AccountNotFoundException e) {
            ErrorResponse error = new ErrorResponse(
                "ACCOUNT_NOT_FOUND",
                e.getMessage(),
                "The requested account does not exist in our system",
                404
            );
            System.out.println(error);
            logger.error("AccountNotFoundException: {}", e.getMessage());
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void demonstrateUnauthorizedAccess() {
        try {
            bankService.checkBalance("ACC001", "9999");
        } catch (UnauthorizedAccessException e) {
            ErrorResponse error = new ErrorResponse(
                "UNAUTHORIZED_ACCESS",
                e.getMessage(),
                "Invalid PIN provided. Access denied for security reasons.",
                401
            );
            System.out.println(error);
            logger.error("UnauthorizedAccessException: {}", e.getMessage());
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void demonstrateInsufficientBalance() {
        try {
            bankService.withdraw("ACC002", "5678", 5000.00);
        } catch (InsufficientBalanceException e) {
            ErrorResponse error = new ErrorResponse(
                "INSUFFICIENT_BALANCE",
                e.getMessage(),
                String.format("Shortfall: $%.2f. Please reduce withdrawal amount or deposit more funds.", 
                             e.getShortfall()),
                400
            );
            System.out.println(error);
            logger.error("InsufficientBalanceException: {}", e.getMessage());
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void demonstrateSuccessfulWithdrawal() {
        try {
            double balanceBefore = bankService.checkBalance("ACC001", "1234");
            System.out.println("Balance before withdrawal: $" + String.format("%.2f", balanceBefore));
            
            bankService.withdraw("ACC001", "1234", 1000.00);
            
            double balanceAfter = bankService.checkBalance("ACC001", "1234");
            System.out.println("✓ SUCCESS: Withdrew $1000.00");
            System.out.println("New balance: $" + String.format("%.2f", balanceAfter));
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void demonstrateSuccessfulTransfer() {
        try {
            System.out.println("Transferring $500 from ACC001 to ACC002...");
            bankService.transfer("ACC001", "1234", "ACC002", 500.00);
            
            double balance1 = bankService.checkBalance("ACC001", "1234");
            double balance2 = bankService.checkBalance("ACC002", "5678");
            
            System.out.println("✓ SUCCESS: Transfer completed");
            System.out.println("ACC001 balance: $" + String.format("%.2f", balance1));
            System.out.println("ACC002 balance: $" + String.format("%.2f", balance2));
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void runInteractiveMode() {
        System.out.println("\n═══════════════════ INTERACTIVE MODE ═══════════════════");
        System.out.println("Sample Accounts:");
        System.out.println("  ACC001 - John Doe (PIN: 1234)");
        System.out.println("  ACC002 - Jane Smith (PIN: 5678)");
        System.out.println("  ACC003 - Bob Johnson (PIN: 9012)");
        
        while (true) {
            System.out.println("\n╔════════════════ MAIN MENU ════════════════╗");
            System.out.println("║ 1. Check Balance                          ║");
            System.out.println("║ 2. Withdraw Money                         ║");
            System.out.println("║ 3. Deposit Money                          ║");
            System.out.println("║ 4. Transfer Money                         ║");
            System.out.println("║ 5. View Account Details                   ║");
            System.out.println("║ 6. Exit                                   ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.print("Select option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                if (choice == 6) {
                    System.out.println("\n✓ Thank you for using TechnoHacks Bank Service!");
                    System.out.println("═══════════════════════════════════════════════════════\n");
                    break;
                }
                
                processMenuChoice(choice);
                
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a number.");
            }
        }
    }
    
    private static void processMenuChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    handleCheckBalance();
                    break;
                case 2:
                    handleWithdrawal();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    handleViewDetails();
                    break;
                default:
                    System.out.println("✗ Invalid option! Please select 1-6.");
            }
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private static void handleCheckBalance() throws AccountNotFoundException, UnauthorizedAccessException {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        
        double balance = bankService.checkBalance(accountNumber, pin);
        System.out.println("\n✓ Current Balance: $" + String.format("%.2f", balance));
    }
    
    private static void handleWithdrawal() throws Exception {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Amount to Withdraw: $");
        double amount = Double.parseDouble(scanner.nextLine());
        
        bankService.withdraw(accountNumber, pin, amount);
        System.out.println("\n✓ Withdrawal successful! Amount: $" + String.format("%.2f", amount));
        
        double newBalance = bankService.checkBalance(accountNumber, pin);
        System.out.println("New Balance: $" + String.format("%.2f", newBalance));
    }
    
    private static void handleDeposit() throws Exception {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Amount to Deposit: $");
        double amount = Double.parseDouble(scanner.nextLine());
        
        bankService.deposit(accountNumber, pin, amount);
        System.out.println("\n✓ Deposit successful! Amount: $" + String.format("%.2f", amount));
        
        double newBalance = bankService.checkBalance(accountNumber, pin);
        System.out.println("New Balance: $" + String.format("%.2f", newBalance));
    }
    
    private static void handleTransfer() throws Exception {
        System.out.print("Enter Source Account Number: ");
        String fromAccount = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Destination Account Number: ");
        String toAccount = scanner.nextLine();
        System.out.print("Enter Amount to Transfer: $");
        double amount = Double.parseDouble(scanner.nextLine());
        
        bankService.transfer(fromAccount, pin, toAccount, amount);
        System.out.println("\n✓ Transfer successful! Amount: $" + String.format("%.2f", amount));
        System.out.println("From: " + fromAccount + " → To: " + toAccount);
    }
    
    private static void handleViewDetails() throws Exception {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        
        BankAccount account = bankService.getAccountDetails(accountNumber, pin);
        System.out.println("\n" + account);
    }
    
    private static void handleException(Exception e) {
        if (e instanceof AccountNotFoundException) {
            AccountNotFoundException anfe = (AccountNotFoundException) e;
            ErrorResponse error = new ErrorResponse(
                "ACCOUNT_NOT_FOUND",
                anfe.getMessage(),
                "Please verify the account number and try again",
                404
            );
            System.out.println(error);
            logger.error("AccountNotFoundException: {}", anfe.getMessage());
            
        } else if (e instanceof UnauthorizedAccessException) {
            UnauthorizedAccessException uae = (UnauthorizedAccessException) e;
            ErrorResponse error = new ErrorResponse(
                "UNAUTHORIZED_ACCESS",
                uae.getMessage(),
                "Invalid credentials. Please check your PIN and try again",
                401
            );
            System.out.println(error);
            logger.error("UnauthorizedAccessException: {}", uae.getMessage());
            
        } else if (e instanceof InsufficientBalanceException) {
            InsufficientBalanceException ibe = (InsufficientBalanceException) e;
            ErrorResponse error = new ErrorResponse(
                "INSUFFICIENT_BALANCE",
                ibe.getMessage(),
                String.format("You need $%.2f more to complete this transaction", ibe.getShortfall()),
                400
            );
            System.out.println(error);
            logger.error("InsufficientBalanceException: {}", ibe.getMessage());
            
        } else {
            ErrorResponse error = new ErrorResponse(
                "GENERAL_ERROR",
                e.getMessage() != null ? e.getMessage() : "An unexpected error occurred",
                "Please contact support if the problem persists",
                500
            );
            System.out.println(error);
            logger.error("Exception: {}", e.getMessage(), e);
        }
    }
    private static String promptValidAccountNumber() {
        while (true) {
            System.out.print("Enter Account Number: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.matches("ACC\\d{3}")) {
                return input;
            } else {
                System.out.println("✗ Invalid account number format. Use ACC followed by 3 digits (e.g., ACC001).");
            }
        }
    }

    private static String promptValidPIN() {
        while (true) {
            System.out.print("Enter PIN: ");
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}")) {
                return input;
            } else {
                System.out.println("✗ Invalid PIN format. PIN must be exactly 4 digits.");
            }
        }
    }
    private static double promptValidAmount(String action) {
        while (true) {
            System.out.print("Enter amount to " + action + ": $");
            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount > 0) {
                    return amount;
                } else {
                    System.out.println("✗ Amount must be greater than zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid amount. Please enter a valid number.");
            }
        }
    }


}