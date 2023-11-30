package nl.pancompany.unicorn.application.unicorn.api;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;

public interface FinancialHealthApi {

    FinancialHealthDto getFinancialHealth(UnicornId unicornId);

    record FinancialHealthDto(UnicornId unicornId, FinancialHealth financialHealth) {

        public enum FinancialHealth {
            EXCELLENT, GOOD, SUFFICIENT, INSUFFICIENT, CRITICAL
        }
    }
}
