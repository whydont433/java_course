package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать!");
        System.out.println("Введите номер карты");
        int cardNumber;
        int cardPIN;
        if (scanner.hasNextInt()){
            cardNumber = scanner.nextInt();
        } else {
            System.out.println("Это не номер карты");
            return;
        }
        System.out.println("Введите PIN карты");
        if (scanner.hasNextInt()){
            cardPIN = scanner.nextInt();
        } else {
            System.out.println("Это не PIN карты");
            return;
        }
        Account testAccount = new Account(12345, 999,
                new BigDecimal(10000).setScale(2, RoundingMode.HALF_UP), BankType.AUM);
        if (cardNumber != testAccount.getCardNumber() || cardPIN !=testAccount.getCardPIN()) {
            System.out.println("Ошибка доступа");
            return;
        }
        CashMachine cashMachine = new CashMachine();
        BigDecimal depositAmount;
        System.out.println("Введите сумму пополнения");
        if (scanner.hasNextBigDecimal()){
            depositAmount = scanner.nextBigDecimal();
        } else {
            System.out.println("Это не сумма пополнения");
            return;
        }
        testAccount.balance=cashMachine.deposit(testAccount.getBalance(), depositAmount);
        System.out.println("Баланс: " + testAccount.getBalance());
        BigDecimal withdrawAmount;
        System.out.println("Введите сумму вывода");
        if (scanner.hasNextBigDecimal()){
            withdrawAmount = scanner.nextBigDecimal();
        } else {
            System.out.println("Это не сумма вывода");
            return;
        }
        testAccount.balance=cashMachine.withdraw(testAccount.getBalance(), withdrawAmount, testAccount.getBankType());
        System.out.println("Баланс: " + testAccount.getBalance());
    }
}
