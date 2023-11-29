package nl.pancompany.unicorn.web.model;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;

public record HealthView (Unicorn.PhysicalHealth physicalHealth, FinancialHealthDto.FinancialHealth financialHealth) {
}
