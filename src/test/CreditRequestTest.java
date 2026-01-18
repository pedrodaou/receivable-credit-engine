package test;

import domain.company.Company;
import domain.credit.CreditRequest;
import domain.credit.CreditStatus;
import domain.receivable.Receivable;
import domain.receivable.ReceivablePortfolio;
import domain.receivable.ReceivableType;
import domain.shared.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditRequestTest {

    private ReceivablePortfolio portfolio;

    @BeforeEach
    public void setUp() {
        Company testCompany = new Company("1", "TestCompany", "12345678000100");

        Receivable receivable = new Receivable(
                new Money(new BigDecimal("1000")),
                LocalDate.now().plusDays(30),
                testCompany,
                ReceivableType.CREDIT_CARD
        );

        portfolio = new ReceivablePortfolio("1", testCompany, List.of(receivable));
    }

    @Test
    public void testCreateValidRequest() {
        CreditRequest creditRequest = new CreditRequest(portfolio, new BigDecimal("500"));
        assertEquals(CreditStatus.PENDING, creditRequest.getCreditStatus());
    }

    @Test
    public void testApprove() {
        CreditRequest creditRequest = new CreditRequest(portfolio, new BigDecimal("800"));
        creditRequest.approve();
        assertEquals(CreditStatus.APPROVED, creditRequest.getCreditStatus());
    }

    @Test
    public void testReject() {
        CreditRequest creditRequest = new CreditRequest(portfolio, new BigDecimal("1500"));
        creditRequest.reject();
        assertEquals(CreditStatus.REJECTED, creditRequest.getCreditStatus());
    }
}
