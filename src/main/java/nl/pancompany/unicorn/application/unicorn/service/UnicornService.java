package nl.pancompany.unicorn.application.unicorn.service;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.unicorn.application.unicorn.dao.Dao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.service.UnicornEnrichmentService;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UnicornService {

    private final Dao<Unicorn, Unicorn.UnicornId> unicornDao;
    private final UnicornEnrichmentService unicornEnrichmentService;

    public UnicornDto getUnicorn(@Valid Unicorn.UnicornId unicornId) throws UnicornNotFoundException, ConstraintViolationException {
        Unicorn unicorn = unicornDao.find(unicornId);
        return unicornEnrichmentService.enrich(unicorn);
    }

}
