# Receivable Credit Engine
Domain-driven credit approval engine based on future receivables as collateral. This project models the core business logic behind approving credit for companies using future receivables as guarantees, with a strong focus on clarity, correctness and Domain-Driven Design (DDD).

---

## Problem
Many small and medium-sized businesses (SMBs / PJs) struggle to access credit through traditional financial institutions due to the lack of conventional collateral. A common alternative is to use **future receivables** (such as credit card, boleto or PIX payments) as guarantees. However, approving credit based on receivables requires **clear business rules**, otherwise the lender is exposed to mispricing risk and overexposure.

---

## Solution
This project implements a credit decision engine that evaluates whether a company is eligible for a loan based on the **risk-adjusted value** of its future receivables. Risk is stratified by two dimensions: **aging** (days to maturity) and **receivable type** (BOLETO, PIX, CONTRACT, etc.). The engine focuses exclusively on the domain layer, intentionally excluding infrastructure concerns such as databases, APIs or external integrations. The goal is to model the problem as close as possible to the business language.

---

## Domain Model

- **Company**
  The legal entity (PJ) requesting credit.

- **Receivable**
  A future cash inflow with a monetary value, due date, owner company and receivable type.

- **ReceivableType**
  Enum representing the origin of the receivable. Each type carries a risk weight reflecting its likelihood of default:

  | Type | Weight |
  |---|---|
  | CONTRACT | 1.00 |
  | SUBSCRIPTION | 0.95 |
  | CREDIT_CARD | 0.90 |
  | DEBIT_CARD | 0.88 |
  | PIX | 0.85 |
  | BOLETO | 0.75 |

- **ReceivablePortfolio — Aggregate Root**
  A collection of receivables associated with a single company. Exposes the total raw portfolio value and protects its internal list from external mutation.

- **CreditRequest**
  Represents a company's credit request with a requested amount, an associated portfolio and a lifecycle status (PENDING → APPROVED / REJECTED).

- **CreditStatus**
  Enum with the lifecycle states of a credit request: `PENDING`, `APPROVED`, `REJECTED`.

- **RiskLevel**
  Enum with aging-based risk levels. Each level carries a weight applied to the receivable value:

  | Days to Maturity | Risk Level | Weight |
  |---|---|---|
  | ≤ 7 days | LOW | 0.85 |
  | ≤ 30 days | VERY_LOW | 0.98 |
  | ≤ 60 days | MEDIUM | 0.88 |
  | ≤ 120 days | HIGH | 0.75 |
  | > 120 days | VERY_HIGH | 0.55 |

- **RiskStratificationCalculator**
  Calculates the risk-adjusted value of a single receivable by combining both the aging weight and the receivable type weight:
  ```
  adjusted = amount × agingWeight × typeWeight
  ```

- **PortfolioRiskStratificationService**
  Iterates all receivables in a portfolio, applies the calculator to each one, and returns a `PortfolioRiskAdjustment` with the original and total adjusted values.

- **PortfolioRiskAdjustment — Value Object**
  Holds both the original and risk-adjusted portfolio values after stratification.

- **CreditApprovalService**
  Domain service responsible for evaluating a credit request against the risk-adjusted portfolio value and updating its status. Contains no infrastructure logic.

- **Money — Value Object**
  Represents a monetary amount with precision, ensuring values are always positive.

---

## Business Rules

- A credit request is **approved** if: risk-adjusted portfolio value ≥ requested amount
- Risk is calculated by combining **aging weight** (days to maturity) and **receivable type weight**
- Receivables must have a **future due date**
- Receivables must have a **positive monetary value**
- All receivables in a portfolio belong to **one single company**

These rules are enforced inside the domain, not in external services.

---

## Architecture

The project follows a layered domain structure with manual dependency injection:

```
CreditApprovalService
    └── PortfolioRiskStratificationService
            └── RiskStratificationCalculator
                    └── RiskLevel + ReceivableType (weights)
```

Each layer only knows the layer immediately below it. `CreditApprovalService` has no knowledge of `RiskLevel` or `ReceivableType` — it only receives the final adjusted value.

---

## Testing

Run tests:
```bash
mvn test
```
Or in your IDE: Right-click `src/test/java/` → Run All Tests

**Test Coverage:**
- **CreditApprovalServiceTest**: Credit approval logic with risk-adjusted portfolio values
- **CreditRequestTest**: Credit request creation and state transitions

---

## TODO / In Progress
- [x] Implement portfolio risk stratification based on receivable aging
- [x] Create PortfolioRiskAdjustment value object
- [x] Create RiskStratificationCalculator
- [x] Create PortfolioRiskStratificationService
- [x] Integrate CreditApprovalService with PortfolioRiskStratificationService
- [x] Add receivable type risk weight to scoring (BOLETO, PIX, CONTRACT, etc.)
- [x] Integrate receivable type risk into RiskStratificationCalculator (combine aging + type weights)
- [ ] Spring Boot REST API integration

---

## Future Enhancements
- Add interest rate calculations based on portfolio composition
- Validate that all receivables in a portfolio belong to the requesting company
- Integrate with external data sources (while maintaining domain purity)

---

## License
This is a learning project created for educational purposes.

---

## Author
Created as a practical exercise in Domain-Driven Design and Java testing.
