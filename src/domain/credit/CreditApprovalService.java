package domain.credit;

import java.math.BigDecimal;
import domain.receivable.Receivable;

public class CreditApprovalService {
    private CreditRequest creditRequest;

    public void evaluateCreditApproval(CreditRequest creditRequest) {
        BigDecimal requestedAmount = creditRequest.getRequestedAmount();
        BigDecimal portfolioValue = creditRequest.getRequestingPortfolio().getTotalPortfolioValue();

        if (portfolioValue.compareTo(requestedAmount) >= 0) { // approving credit only if portfolio value >= requested amount
            creditRequest.approve();
        }
        else {
            creditRequest.reject();
        }
    }
}