package nl.pancompany.unicorn.context.application.unicorn;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi;
import nl.pancompany.unicorn.application.unicorn.dao.Dao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.service.UnicornEnrichmentService;
import nl.pancompany.unicorn.application.unicorn.mapper.LegDtoMapper;
import nl.pancompany.unicorn.application.unicorn.mapper.UnicornDtoMapper;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;

@Getter
public class UnicornContext {

    private final UnicornService unicornService;
    private final UnicornLegService unicornLegService;

    public UnicornContext(Dao<Unicorn, Unicorn.UnicornId> unicornDao, FinancialHealthApi financialHealthApi) {
        this.unicornService = createUnicornService(unicornDao, financialHealthApi);
        this.unicornLegService = createUnicornLegService(unicornDao);
    }

    private UnicornService createUnicornService(Dao<Unicorn, Unicorn.UnicornId> unicornDao, FinancialHealthApi financialHealthApi) {
        UnicornEnrichmentService unicornEnrichmentService = new UnicornEnrichmentService(financialHealthApi,
                UnicornDtoMapper.INSTANCE);
        return new UnicornService(unicornDao, unicornEnrichmentService);
    }

    private UnicornLegService createUnicornLegService(Dao<Unicorn, Unicorn.UnicornId> unicornDao) {
        return new UnicornLegService(unicornDao, LegDtoMapper.INSTANCE);
    }
}
