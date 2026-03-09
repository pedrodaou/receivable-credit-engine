package domain.receivable;

import java.math.BigDecimal;

public enum ReceivableType {
    CONTRACT(new BigDecimal("1.00")),
    SUBSCRIPTION(new BigDecimal("0.95")),
    CREDIT_CARD(new BigDecimal("0.90")),
    DEBIT_CARD(new BigDecimal("0.88")),
    PIX(new BigDecimal("0.85")),
    BOLETO(new BigDecimal("0.75"));

    private final BigDecimal weight;

    ReceivableType(BigDecimal weight) {
        this.weight = weight;
    }
    public BigDecimal getWeight() {
        return weight;
    }
}