package nl.pancompany.unicorn.context.application.adapter;

import lombok.Getter;
import nl.pancompany.unicorn.application.finance.service.SalesService;
import nl.pancompany.unicorn.application.adapter.financeadapter.FinancialHealthAdapter;
import nl.pancompany.unicorn.application.adapter.financeadapter.FinancialHealthMapper;

@Getter
public class FinanceAdapterContext {

    FinancialHealthAdapter financialHealthAdapter;

    public FinanceAdapterContext(SalesService salesService) {
        this.financialHealthAdapter = new FinancialHealthAdapter(salesService, FinancialHealthMapper.INSTANCE);
    }
}
