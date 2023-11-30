package nl.pancompany.unicorn.context.application.finance;

import lombok.Getter;
import nl.pancompany.unicorn.application.finance.service.SalesService;

@Getter
public class FinanceContext {

    private final SalesService salesService;

    public FinanceContext() {
        this.salesService = new SalesService();
    }
}
