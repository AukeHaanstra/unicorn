package nl.pancompany.unicorn.application.unicorn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.unicorn.application.unicorn.repository.Repository;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.domain.service.UnicornEnrichmentService;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import nl.pancompany.unicorn.application.unicorn.exception.UnicornNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class UnicornService {

    private final Repository<Unicorn, UnicornId> unicornRepository;
    private final UnicornEnrichmentService unicornEnrichmentService;

    public UnicornDto getUnicorn(UnicornId unicornId) throws UnicornNotFoundException {
        Unicorn unicorn = unicornRepository.find(unicornId);
        return unicornEnrichmentService.enrich(unicorn);
    }

}
