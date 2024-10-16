package nl.pancompany.unicorn.application.adapter.financeadapter;

import lombok.RequiredArgsConstructor;
import nl.pancompany.unicorn.application.finance.dto.TotalSalesDto;
import nl.pancompany.unicorn.application.finance.service.SalesService;
import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;

@RequiredArgsConstructor
public class FinancialHealthAdapter implements FinancialHealthApi {

    private final SalesService salesService;
    private final FinancialHealthMapper mapper;

    public FinancialHealthDto getFinancialHealth(Unicorn.UnicornId unicornId) {
        TotalSalesDto totalSalesDto = salesService.calculateTotalSales(unicornId);
        return mapper.toFinancialHealthDto(totalSalesDto);
    }
}