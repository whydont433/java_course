package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface WithdrawalOperations {

    default BigDecimal applyCommission(BigDecimal amount, BankType currentBank) {
        if (amount == null || currentBank == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal amountOfFee = amount.multiply(currentBank.getBankFee());
        return amountOfFee.setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal withdraw(BigDecimal currentBalance, BigDecimal withdrawalAmount, BankType currentBank);


}
