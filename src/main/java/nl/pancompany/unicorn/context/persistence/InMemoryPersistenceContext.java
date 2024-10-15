package nl.pancompany.unicorn.context.persistence;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.repository.UnicornRepository;
import nl.pancompany.unicorn.persistence.database.UnicornJpaMapper;
import nl.pancompany.unicorn.persistence.inmemory.InMemoryUnicornRepository;

@Getter
public class InMemoryPersistenceContext {

    private final UnicornRepository unicornRepository;

    public InMemoryPersistenceContext() {
        this.unicornRepository = new InMemoryUnicornRepository(UnicornJpaMapper.INSTANCE);
    }
}
