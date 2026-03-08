package domain.credit;

import java.math.BigDecimal;

import domain.credit.scoring.PortfolioRiskAdjustment;
import domain.credit.scoring.PortfolioRiskStratificationService;
import domain.receivable.ReceivablePortfolio;

public class CreditApprovalService {
    PortfolioRiskStratificationService riskService;

    public CreditApprovalService (PortfolioRiskStratificationService riskService){ // injecting dependency to be used
        this.riskService = riskService;
    }

    public void evaluateCreditApproval(CreditRequest creditRequest) {
        BigDecimal requestedAmount = creditRequest.getRequestedAmount();

        ReceivablePortfolio portfolio = creditRequest.getRequestingPortfolio();

        PortfolioRiskAdjustment adjustedValue = riskService.adjustedPortfolioByRisk(portfolio);

        if (adjustedValue.getAdjustedPortfolioValue().compareTo(requestedAmount) >= 0) { // approving credit only if portfolio value >= requested amount
            creditRequest.approve();
        }
        else {
            creditRequest.reject();
        }
    }
}