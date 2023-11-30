package nl.pancompany.unicorn.application.unicorn.domain.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi;
import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi.FinancialHealthDto;
import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi.FinancialHealthDto.FinancialHealth;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.PhysicalHealth;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import nl.pancompany.unicorn.application.unicorn.mapper.UnicornDtoMapper;

@RequiredArgsConstructor
public class UnicornEnrichmentService {

    private final FinancialHealthApi financialHealthApi;
    private final UnicornDtoMapper unicornDtoMapper;

    public UnicornDto enrich(Unicorn unicorn) {
        FinancialHealthDto financialHealthDto = financialHealthApi.getFinancialHealth(unicorn.getUnicornId());
        UnicornDto unicornDto = unicornDtoMapper.map(unicorn, financialHealthDto);
        unicornDto.health().setFinancialHealth(financialHealthDto.financialHealth()); // Workaround for Mapstruct bug
        return unicornDto;
    }

    @Data
    public static class HealthDto {

        private PhysicalHealth physicalHealth;
        private FinancialHealth financialHealth;
    }
}
