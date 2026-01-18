package domain.credit;

import domain.receivable.ReceivablePortfolio;
import domain.shared.DomainException;

import java.math.BigDecimal;

public class CreditRequest {
    private final ReceivablePortfolio requestingPortfolio;
    private final BigDecimal requestedAmount;
    private CreditStatus creditStatus;

    public CreditRequest(ReceivablePortfolio requestingPortfolio,  BigDecimal requestedAmount) {
        if (requestingPortfolio == null || requestingPortfolio.getCompany() == null) {
            throw new DomainException ("Portfolio and or company must not be null.");
        }

        if (requestedAmount == null || requestedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException ("Amount must not be null, zero or negative");
        }

        this.requestingPortfolio = requestingPortfolio;
        this.requestedAmount = requestedAmount;
        this.creditStatus = CreditStatus.PENDING; // initial state
    }

    public void approve(){
        if (this.creditStatus != CreditStatus.PENDING) {
            throw new DomainException ("Credit status must be pending to be approved.");
        }
        this.creditStatus = CreditStatus.APPROVED;
    }

    public void reject(){
        if(this.creditStatus != CreditStatus.PENDING){
            throw new DomainException ("Credit status must be pending to be rejected.");
        }
        this.creditStatus = CreditStatus.REJECTED;
    }

    public ReceivablePortfolio getRequestingPortfolio() {
        return requestingPortfolio;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public CreditStatus getCreditStatus() {
        return creditStatus;
    }
}