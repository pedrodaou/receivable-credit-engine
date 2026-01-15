package domain.shared;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal value;

    public Money(BigDecimal value) {
        this.value = value;
    }

    public boolean isZeroOrNegative() {
        return value.compareTo(BigDecimal.ZERO) <= 0;
    }
}
