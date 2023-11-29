package nl.pancompany.unicorn.financeadapter;

import lombok.RequiredArgsConstructor;
import nl.pancompany.unicorn.application.finance.dto.SalesDto;
import nl.pancompany.unicorn.application.finance.service.SalesService;
import nl.pancompany.unicorn.application.unicorn.adapter.FinancialHealthAdapter;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalesToFinancialHealthAdapter implements FinancialHealthAdapter {

    private final SalesService salesService;
    private final SalesToFinancialHealthMapper mapper;

    public FinancialHealthDto getFinancialHealth(Unicorn.UnicornId unicornId) {
        SalesDto salesDto = salesService.calculateTotalSales(unicornId);
        return mapper.toFinancialHealthDto(salesDto);
    }
}