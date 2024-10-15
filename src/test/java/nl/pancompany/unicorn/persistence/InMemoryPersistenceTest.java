package nl.pancompany.unicorn.persistence;

import nl.pancompany.unicorn.context.persistence.InMemoryPersistenceContext;
import org.junit.jupiter.api.BeforeEach;

class InMemoryPersistenceTest extends PersistenceTest {

    @BeforeEach
    public void setup() {
        InMemoryPersistenceContext inMemoryPersistenceContext = new InMemoryPersistenceContext();
        unicornRepository = inMemoryPersistenceContext.getUnicornRepository();
    }
}
