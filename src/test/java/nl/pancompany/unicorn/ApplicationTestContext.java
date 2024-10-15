package nl.pancompany.unicorn;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.repository.UnicornRepository;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.context.application.ApplicationContext;
import nl.pancompany.unicorn.context.persistence.InMemoryPersistenceContext;

@Getter
public class ApplicationTestContext {

    private final UnicornService unicornService;
    private final UnicornLegService unicornLegService;
    private final UnicornRepository unicornRepository;

    public ApplicationTestContext() {
        InMemoryPersistenceContext inMemoryPersistenceContext = new InMemoryPersistenceContext();
        this.unicornRepository = inMemoryPersistenceContext.getUnicornRepository();
        ApplicationContext applicationContext = new ApplicationContext(unicornRepository);
        this.unicornService = applicationContext.getUnicornService();
        this.unicornLegService = applicationContext.getUnicornLegService();
    }
}
