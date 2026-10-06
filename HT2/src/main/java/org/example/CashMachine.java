package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CashMachine implements WithdrawalOperations, DepositOperations {

    @Override
    public BigDecimal deposit(BigDecimal currentBalance, BigDecimal depositAmount) {
        if (currentBalance == null) {
            currentBalance = BigDecimal.ZERO.setScale(2,RoundingMode.HALF_UP);
        }
        if ( depositAmount == null || depositAmount.compareTo(BigDecimal.ZERO) <=0) {
            return currentBalance.setScale(2, RoundingMode.HALF_UP);
        }
        return currentBalance.add(depositAmount).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal withdraw(BigDecimal currentBalance, BigDecimal withdrawalAmount, BankType currentBank) {
        if (currentBalance == null) {
            currentBalance = BigDecimal.ZERO.setScale(2,RoundingMode.HALF_UP);
        }
        if (withdrawalAmount == null || withdrawalAmount.compareTo(BigDecimal.ZERO) <=0) {
            return currentBalance.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal withdrawalAmountOfFee = this.applyCommission(withdrawalAmount, currentBank);
        BigDecimal withdrawalAmountWithFee = withdrawalAmount.add(withdrawalAmountOfFee);
        if (withdrawalAmountWithFee.compareTo(currentBalance) > 0) {
            System.out.println("Недостаточно средств");
            return currentBalance.setScale(2,RoundingMode.HALF_UP);
        }
        return currentBalance.subtract(withdrawalAmountWithFee).setScale(2, RoundingMode.HALF_UP);


    }
}
