package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    public int cardNumber;
    public int cardPIN;
    public BigDecimal balance;
    public BankType bankType;

    public Account(int cardNumber, int cardPIN, BigDecimal balance, BankType bankType) {
        if (cardNumber >= 10000 && cardNumber <= 99999) {
            this.cardNumber = cardNumber;
        } else this.cardNumber = 0;

        if (cardPIN >= 100 && cardPIN <= 999) {
            this.cardPIN = cardPIN;
        } else this.cardPIN = 0;

        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        } else this.balance = balance.setScale(2, RoundingMode.HALF_UP);

        if (bankType == null) {
            this.bankType = BankType.NEO;
        } else this.bankType = bankType;

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getCardPIN() {
        return cardPIN;
    }

    public BankType getBankType() {
        return bankType;
    }

    @Override
    public String toString() {
        return this.bankType.getBankName() + " Карта: " + this.getCardNumber() + ", Баланс: " + this.getBalance() + " руб.";
    }
}
