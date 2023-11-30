package nl.pancompany.unicorn.web.model;

import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;

public record HealthView (Unicorn.PhysicalHealth physicalHealth, FinancialHealthApi.FinancialHealthDto.FinancialHealth financialHealth) {
}
