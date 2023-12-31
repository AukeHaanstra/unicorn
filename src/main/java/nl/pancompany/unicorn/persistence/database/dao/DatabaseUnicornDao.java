package nl.pancompany.unicorn.persistence.database.dao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.pancompany.unicorn.application.unicorn.dao.UnicornDao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.exception.UnicornAlreadyExistsException;
import nl.pancompany.unicorn.application.unicorn.exception.UnicornNotFoundException;
import nl.pancompany.unicorn.persistence.database.model.UnicornJpaEntity;
import nl.pancompany.unicorn.persistence.database.repository.UnicornRepo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
@Transactional
public class DatabaseUnicornDao implements UnicornDao {

    private final UnicornRepo unicornRepo;
    private final UnicornJpaMapper mapper;

    @Override
    public Unicorn find(UnicornId unicornId) throws UnicornNotFoundException {
        requireNonNull(unicornId);
        UnicornJpaEntity unicorn = unicornRepo.findByUnicornId(unicornId.toStringValue())
                .orElseThrow(UnicornNotFoundException::new);
        return mapper.map(unicorn);
    }

    @Override
    public Unicorn add(Unicorn unicorn) {
        requireNonNull(unicorn);
        if (unicornRepo.existsByUnicornId(unicorn.getUnicornId().toStringValue())) {
            throw new UnicornAlreadyExistsException();
        }
        return mapper.map(unicornRepo.save(mapper.map(unicorn)));
    }

    @Override
    public Unicorn update(Unicorn unicorn) {
        requireNonNull(unicorn);
        UnicornJpaEntity persistedUnicorn = unicornRepo.findByUnicornId(unicorn.getUnicornId().toStringValue())
                .orElseThrow(UnicornNotFoundException::new);
        UnicornJpaEntity unicornToMerge = mapper.map(unicorn);
        unicornToMerge.setId(persistedUnicorn.getId());
        return mapper.map(unicornRepo.save(unicornToMerge));
    }

    @Override
    public long count() {
        return unicornRepo.count();
    }
}
