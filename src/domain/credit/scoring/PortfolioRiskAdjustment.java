package domain.credit.scoring;

import java.math.BigDecimal;

public class PortfolioRiskAdjustment { // value object
    private final BigDecimal originalPortfolioValue;
    private final BigDecimal adjustedPortfolioValue;

    public PortfolioRiskAdjustment(BigDecimal originalPortfolioValue, BigDecimal adjustedPortfolioValue) {
        this.originalPortfolioValue = originalPortfolioValue;
        this.adjustedPortfolioValue = adjustedPortfolioValue;
    }

    public BigDecimal getOriginalPortfolioValue() {
        return originalPortfolioValue;
    }

    public BigDecimal getAdjustedPortfolioValue() {
        return adjustedPortfolioValue;
    }
}
