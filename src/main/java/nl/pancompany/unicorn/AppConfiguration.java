package nl.pancompany.unicorn;

import nl.pancompany.unicorn.application.unicorn.dao.Dao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.context.application.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ApplicationContext applicationContext(Dao<Unicorn, Unicorn.UnicornId> unicornDao) {
        return new ApplicationContext(unicornDao);
    }

    @Bean
    public UnicornService unicornService(ApplicationContext applicationContext) {
        return applicationContext.getUnicornService();
    }

    @Bean
    public UnicornLegService unicornLegService(ApplicationContext applicationContext) {
        return applicationContext.getUnicornLegService();
    }

}
