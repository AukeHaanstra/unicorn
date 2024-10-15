package nl.pancompany.unicorn.context.application.unicorn;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.api.FinancialHealthApi;
import nl.pancompany.unicorn.application.unicorn.repository.Repository;
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

    public UnicornContext(Repository<Unicorn, Unicorn.UnicornId> unicornRepository, FinancialHealthApi financialHealthApi) {
        this.unicornService = createUnicornService(unicornRepository, financialHealthApi);
        this.unicornLegService = createUnicornLegService(unicornRepository);
    }

    private UnicornService createUnicornService(Repository<Unicorn, Unicorn.UnicornId> unicornRepository, FinancialHealthApi financialHealthApi) {
        UnicornEnrichmentService unicornEnrichmentService = new UnicornEnrichmentService(financialHealthApi,
                UnicornDtoMapper.INSTANCE);
        return new UnicornService(unicornRepository, unicornEnrichmentService);
    }

    private UnicornLegService createUnicornLegService(Repository<Unicorn, Unicorn.UnicornId> unicornRepository) {
        return new UnicornLegService(unicornRepository, LegDtoMapper.INSTANCE);
    }
}
