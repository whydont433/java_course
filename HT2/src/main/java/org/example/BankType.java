package org.example;

import java.math.BigDecimal;

public enum BankType {
    NEO("НеоКредит Банк", "0.01"), AUM("Арум Финтех", "0.02"), VTA("Вектор Альянс Банк", "0.00");

    public final String bankName;
    public final BigDecimal bankFee;

    BankType(String bankName, String bankFee) {
        this.bankName = bankName;
        this.bankFee = new BigDecimal(bankFee);
    }

    public String getBankName() {
        return this.bankName;
    }

    public BigDecimal getBankFee () {
        return this.bankFee;
    }
}
