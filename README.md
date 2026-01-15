# Receivable Credit Engine

Domain-driven credit approval engine based on future receivables as collateral.

This project models the **core business logic** behind approving credit for
companies using **future receivables** as guarantees, with a strong focus on
**clarity, correctness and Domain-Driven Design (DDD)**.

---

## Problem

Many small and medium-sized businesses (SMBs / PJs) struggle to access credit
through traditional financial institutions due to the lack of conventional
collateral.

A common alternative is to use **future receivables** (such as credit card,
boleto or PIX payments) as guarantees. However, approving credit based on
receivables requires **clear business rules**, otherwise the lender is exposed
to mispricing risk and overexposure.

---

## Solution

This project implements a **simple credit decision engine** that evaluates
whether a company is eligible for a loan based on the **total value of its
future receivables**.

The engine focuses **exclusively on the domain layer**, intentionally excluding
infrastructure concerns such as databases, APIs or external integrations.

The goal is to model the problem **as close as possible to the business language**.

---

## Domain Model

The core concepts of the domain are:

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
  - BOLETO
  - PIX
  - SUBSCRIPTION
  - CONTRACT

- **ReceivablePortfolio**  
  A collection of receivables associated with a single company.

- **CreditApprovalService**  
  A domain service responsible for deciding whether a credit request should be
  approved based on business rules.

---

## Business Rules

The current credit approval rules are intentionally simple:

- A credit request is **approved only if** the sum of receivables is **greater
  than or equal to** the requested loan amount.
- Receivables must have a **future due date**.
- Receivables must have a **positive value**.
- All receivables in a portfolio belong to **one single company**.

These rules are enforced **inside the domain**, not in external services.
