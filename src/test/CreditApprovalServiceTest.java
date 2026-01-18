package test;

import domain.company.Company;
import domain.credit.CreditApprovalService;
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

public class CreditApprovalServiceTest {

    private CreditApprovalService service;
    private ReceivablePortfolio portfolio;

    @BeforeEach
    public void setUp() {
        service = new CreditApprovalService();
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
    public void testApprovedWhenPortfolioGreater(){
        CreditRequest request1 = new CreditRequest(portfolio, new BigDecimal("500"));
        service.evaluateCreditApproval(request1);
        assertEquals(CreditStatus.APPROVED, request1.getCreditStatus());
    }

    @Test
    public void testApprovedWhenPortfolioEquals(){
        CreditRequest request2 = new CreditRequest(portfolio, new BigDecimal("1000"));
        service.evaluateCreditApproval(request2);
        assertEquals(CreditStatus.APPROVED, request2.getCreditStatus());
    }

    @Test
    public void testApprovedWhenPortfolioLess(){
        CreditRequest request3 = new CreditRequest(portfolio, new BigDecimal("2000"));
        service.evaluateCreditApproval(request3);
        assertEquals(CreditStatus.REJECTED, request3.getCreditStatus());
    }
}