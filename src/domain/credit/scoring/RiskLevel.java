package domain.credit.scoring;

import java.math.BigDecimal;

public enum RiskLevel {
    VERY_LOW(new BigDecimal("0.98")), // label + value
    LOW(new BigDecimal("0.85")),
    MEDIUM(new BigDecimal("0.88")),
    HIGH(new BigDecimal("0.75")),
    VERY_HIGH(new BigDecimal("0.55"));

    private final BigDecimal weight;

    RiskLevel(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}