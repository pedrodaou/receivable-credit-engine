package domain.credit.scoring;

import domain.receivable.Receivable;
import domain.receivable.ReceivablePortfolio;

import java.math.BigDecimal;

public class PortfolioRiskStratificationService { // service
    private final RiskStratificationByAgingCalculator calculator;

    // injects calculator dependency in this service to use it to adjust the portfolio value
    public PortfolioRiskStratificationService(RiskStratificationByAgingCalculator calculator) {
        this.calculator = calculator;
    }

    public PortfolioRiskAdjustment adjustedPortfolioByRisk(ReceivablePortfolio portfolio) {
        BigDecimal originalValue = portfolio.getTotalPortfolioValue();
        BigDecimal adjustedValue = BigDecimal.ZERO;

        for (Receivable receivable : portfolio.getReceivables()){
            BigDecimal adjusted = calculator.calculateAdjustedValue(receivable); // receiving receivables adjusted value
            adjustedValue =  adjustedValue.add(adjusted);
        }
        return new PortfolioRiskAdjustment(originalValue, adjustedValue); // returning by its constructor and parameters
    }
}