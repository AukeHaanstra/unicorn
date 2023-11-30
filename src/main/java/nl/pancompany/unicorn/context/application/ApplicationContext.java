package nl.pancompany.unicorn.context.application;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.dao.Dao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.context.application.adapter.FinanceAdapterContext;
import nl.pancompany.unicorn.context.application.finance.FinanceContext;
import nl.pancompany.unicorn.context.application.unicorn.UnicornContext;

@Getter
public class ApplicationContext {

    private final UnicornService unicornService;
    private final UnicornLegService unicornLegService;

    public ApplicationContext(Dao<Unicorn, Unicorn.UnicornId> unicornDao) {
        FinanceContext financeContext = new FinanceContext();
        FinanceAdapterContext financeAdapterContext = new FinanceAdapterContext(financeContext.getSalesService());
        UnicornContext unicornContext = new UnicornContext(unicornDao, financeAdapterContext.getFinancialHealthAdapter());
        this.unicornService = unicornContext.getUnicornService();
        this.unicornLegService = unicornContext.getUnicornLegService();
    }
}
