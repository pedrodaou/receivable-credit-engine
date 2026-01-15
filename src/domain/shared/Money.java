package domain.shared;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal value;

    public Money(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0){ //returns either 1, 0 or -1
            throw new DomainException("Value must not be null, zero or negative.");
        }

        this.value = value;
    }

    public boolean isZeroOrNegative() {
        return value.compareTo(BigDecimal.ZERO) <= 0;
    }

    public BigDecimal getValue() {
        return value;
    }
}
