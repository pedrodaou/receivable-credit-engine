package domain.credit.scoring;

import domain.receivable.Receivable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RiskStratificationByAgingCalculator {

    // main method that will be called in PortfolioRiskStratificationService
    public BigDecimal calculateAdjustedValue (Receivable receivable){
        long daysToMaturity = ChronoUnit.DAYS.between(LocalDate.now(), receivable.getDueDate());
        RiskLevel riskLevel = determineRiskLevel(daysToMaturity);

        return receivable.getAmount().getValue().multiply(riskLevel.getWeight()); //multiplying by weight from label
    }

    private RiskLevel determineRiskLevel (long daysToMaturity){
        if(daysToMaturity <= 7) return RiskLevel.LOW; // only labeling, not adding it's value
        if (daysToMaturity <= 30) return RiskLevel.VERY_LOW;
        if  (daysToMaturity <= 60) return RiskLevel.MEDIUM;
        if  (daysToMaturity <= 120) return RiskLevel.HIGH;
        return RiskLevel.VERY_HIGH;
    }
}