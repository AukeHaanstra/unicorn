package nl.pancompany.unicorn.application.unicorn.dto;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;

public record FinancialHealthDto(UnicornId unicornId, FinancialHealth financialHealth) {
    public enum FinancialHealth {
        EXCELLENT, GOOD, SUFFICIENT, INSUFFICIENT, CRITICAL
    }
}
