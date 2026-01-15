package domain.receivable;

import domain.company.Company;
import domain.shared.DomainException;
import domain.shared.Money;
import java.time.LocalDate;

public class Receivable {
    private final Money amount;
    private final LocalDate dueDate;
    private final Company owner;
    private final ReceivableType receivableType;

    public Receivable(Money amount, LocalDate dueDate, Company owner, ReceivableType receivableType){
        if (amount == null || amount.isZeroOrNegative()){
            throw new DomainException("Amount must not be null, zero or negative");
        }
        if (dueDate == null || !dueDate.isAfter(LocalDate.now())){ // only future receivables can be used for credit requests
            throw new DomainException("DueDate must be in the future and not null.");
        }
        if (owner == null){
            throw new DomainException("Owner must not be null.");
        }

        if (receivableType == null){
            throw new DomainException("ReceivableType must not be null.");
        }

        this.amount = amount;
        this.dueDate = dueDate;
        this.owner = owner;
        this.receivableType = receivableType;
    }

    public boolean isFuture(LocalDate today){
        if (today == null){
            throw new DomainException("Today must not be null.");
        }
        return dueDate.isAfter(today); // checking if the date given isFuture
    }


    public Money getAmount(){
        return amount;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public Company getOwner(){
        return owner;
    }

    public ReceivableType getReceivableType(){
        return receivableType;
    }

}
