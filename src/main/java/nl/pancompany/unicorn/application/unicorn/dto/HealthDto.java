package nl.pancompany.unicorn.application.unicorn.dto;

import lombok.Data;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.PhysicalHealth;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto.FinancialHealth;

@Data
public class HealthDto {

    private PhysicalHealth physicalHealth;
    private FinancialHealth financialHealth;
}
