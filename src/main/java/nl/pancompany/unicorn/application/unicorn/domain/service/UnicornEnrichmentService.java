package nl.pancompany.unicorn.application.unicorn.domain.service;

import lombok.RequiredArgsConstructor;
import nl.pancompany.unicorn.application.finance.service.SalesService;
import nl.pancompany.unicorn.application.unicorn.adapter.FinancialHealthAdapter;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import nl.pancompany.unicorn.application.unicorn.mapper.UnicornDtoMapper;
import nl.pancompany.unicorn.financeadapter.SalesToFinancialHealthAdapter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnicornEnrichmentService {

    private final FinancialHealthAdapter financialHealthAdapter;
    private final UnicornDtoMapper unicornDtoMapper;

    public UnicornDto enrich(Unicorn unicorn) {
        FinancialHealthDto financialHealthDto = financialHealthAdapter.getFinancialHealth(unicorn.getUnicornId());
        UnicornDto unicornDto = unicornDtoMapper.map(unicorn, financialHealthDto);
        unicornDto.health().setFinancialHealth(financialHealthDto.financialHealth()); // Workaround for Mapstruct bug
        return unicornDto;
    }
}
