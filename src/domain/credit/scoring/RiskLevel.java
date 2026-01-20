package domain.credit.scoring;

import java.math.BigDecimal;

public enum RiskLevel {
    VERY_LOW(new BigDecimal("0.98")),
    LOW(new BigDecimal("0.85")),
    MEDIUM(new BigDecimal("0.88")),
    HIGH(new BigDecimal("0.75")),
    VERY_HIGH(new BigDecimal("0.55"));

    private final BigDecimal riskLevel;

    RiskLevel(BigDecimal riskLevel) {
        this.riskLevel = riskLevel;
    }

    public BigDecimal getRiskLevel() {
        return riskLevel;
    }
}