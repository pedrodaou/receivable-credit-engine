package domain.receivable;

import domain.company.Company;
import domain.shared.DomainException;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class ReceivablePortfolio {
    private final String id;
    private final Company company;
    private final List<Receivable> receivables;

    public ReceivablePortfolio (String id, Company company, List<Receivable> receivables){
        if (id == null){
            throw new DomainException("id must not be null.");
        }
        if (company == null){
            throw new DomainException("company must not be null.");
        }

        if (receivables == null){
            throw new DomainException("receivables must not be null.");
        }

        this.id = id;
        this.company = company;
        this.receivables = new ArrayList<>(receivables); //changing so it doesn't get corrupted from external agents
    }

    public void addReceivable(Receivable receivable){
        if (receivable == null){
            throw new DomainException("Receivable must not be null.");
        }
        receivables.add(receivable);
    }

    public BigDecimal getTotalPortfolioValue(){
        if(receivables.isEmpty()) {
            throw new DomainException("Receivables must not be empty.");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Receivable receivable : receivables){
            total = total.add(receivable.getAmount().getValue()); //get updated amount, then add the new value to it.
        }
        return total;
    }

    public String getId(){
        return id;
    }

    public Company getCompany(){
        return company;
    }

    public List<Receivable> getReceivables(){
        return List.copyOf(receivables); //
    }
}
