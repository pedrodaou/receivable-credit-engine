# Receivable Credit Engine

Domain-driven credit approval engine based on future receivables as collateral. This project models the core business logic behind approving credit for companies using future receivables as guarantees, with a strong focus on clarity, correctness and Domain-Driven Design (DDD).

---

## Problem

Many small and medium-sized businesses (SMBs / PJs) struggle to access credit through traditional financial institutions due to the lack of conventional collateral. A common alternative is to use **future receivables** (such as credit card, boleto or PIX payments) as guarantees. However, approving credit based on receivables requires **clear business rules**, otherwise the lender is exposed to mispricing risk and overexposure.

---

## Solution

This project implements a simple credit decision engine that evaluates whether a company is eligible for a loan based on the total value of its future receivables. The engine focuses exclusively on the domain layer, intentionally excluding infrastructure concerns such as databases, APIs or external integrations. The goal is to model the problem as close as possible to the business language.

---

## Domain Model

- **Company**  
  The legal entity (PJ) requesting credit.

- **Receivable**  
  A future cash inflow with:
  - monetary value
  - due date
  - source/type (e.g. credit card, boleto, PIX)
  
- **ReceivableSource**  
  An enum representing the origin of the receivable, such as:
  - CREDIT_CARD
  - DEBIT_CARD
  - BOLETO
  - PIX
  - SUBSCRIPTION
  - CONTRACT

- **ReceivablePortfolio - Aggregate Root**  
  A collection of receivables associated with a single company.

- **CreditApprovalService**  
  A domain service responsible for deciding whether a credit request should be
  approved based on business rules.

- **CreditRequest**  
  Represents a company's credit request with:
  - requested loan amount
  - associated receivable portfolio
  - credit status (PENDING, APPROVED, REJECTED)

- **CreditStatus**  
  Lifecycle states of a credit request:
  - PENDING: Awaiting evaluation
  - APPROVED: Credit was approved
  - REJECTED: Credit was rejected

- **CreditApprovalService**  
  Domain service responsible for evaluating a credit request and updating its status based on business rules. Contains no infrastructure logic.

- **Money - Value Object**  
  Represents a monetary amount with precision, ensuring values are always positive.

---

## Business Rules

The current credit approval rules are intentionally simple:

- A credit request is **approved** if: receivables ≥ requested amount
- Receivables must have a **future due date**
- Receivables must have a **positive value**
- All receivables belong to **one single company**

These rules are enforced inside the domain, not in external services.

---

## Testing

Run tests:

```bash
mvn test
```

Or in your IDE: Right-click `src/test/java/` → Run All Tests

**Test Coverage:**
- **CreditApprovalServiceTest**: Credit approval logic
- **CreditRequestTest**: Credit request creation and state transitions

## TODO / In Progress
- [x] Implement portfolio risk stratification based on receivable aging
- [x] Create PortfolioRiskAdjustment value object
- [x] Create RiskStratificationByAgingCalculator
- [x] Create PortfolioRiskStratificationService
- [ ] Integrate CreditApprovalService with PortfolioRiskStratificationService

## Future Enhancements

- Add more sophisticated credit scoring rules
- Implement receivable aging and risk stratification
- Add interest rate calculations based on portfolio composition
- Integrate with external data sources (while maintaining domain purity)

---

## License

This is a learning project created for educational purposes.

---

## Author

Created as a practical exercise in Domain-Driven Design and Java testing.
