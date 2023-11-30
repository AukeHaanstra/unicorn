package nl.pancompany.unicorn;

import lombok.Getter;
import nl.pancompany.unicorn.application.unicorn.dao.UnicornDao;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.context.application.ApplicationContext;
import nl.pancompany.unicorn.context.persistence.InMemoryPersistenceContext;

@Getter
public class ApplicationTestContext {

    private final UnicornService unicornService;
    private final UnicornLegService unicornLegService;
    private final UnicornDao unicornDao;

    public ApplicationTestContext() {
        InMemoryPersistenceContext inMemoryPersistenceContext = new InMemoryPersistenceContext();
        this.unicornDao = inMemoryPersistenceContext.getUnicornDao();
        ApplicationContext applicationContext = new ApplicationContext(unicornDao);
        this.unicornService = applicationContext.getUnicornService();
        this.unicornLegService = applicationContext.getUnicornLegService();
    }
}
