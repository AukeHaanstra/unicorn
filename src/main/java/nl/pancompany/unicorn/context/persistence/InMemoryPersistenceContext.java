package nl.pancompany.unicorn.context.persistence;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.dao.UnicornDao;
import nl.pancompany.unicorn.persistence.database.dao.UnicornJpaMapper;
import nl.pancompany.unicorn.persistence.inmemory.dao.InMemoryUnicornDao;

@Getter
public class InMemoryPersistenceContext {

    private final UnicornDao unicornDao;

    public InMemoryPersistenceContext() {
        this.unicornDao = new InMemoryUnicornDao(UnicornJpaMapper.INSTANCE);
    }
}
