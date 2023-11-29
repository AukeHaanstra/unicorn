package nl.pancompany.unicorn.application;

import nl.pancompany.unicorn.application.unicorn.dao.UnicornDao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import nl.pancompany.unicorn.application.unicorn.service.UnicornNotFoundException;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.testbuilders.UnicornTestBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class GetUnicornTest {

    @Autowired
    UnicornDao unicornDao;

    @Autowired
    UnicornService unicornService;

    Unicorn savedUnicorn;

    @BeforeEach
    public void setup() {
        savedUnicorn = unicornDao.add(new UnicornTestBuilder().defaults().build());
    }

    @Test
    void findsUnicorn() {
        UnicornDto unicornDto = unicornService.getUnicorn(savedUnicorn.getUnicornId());

        Assertions.assertThat(unicornDto.unicornId()).isEqualTo(savedUnicorn.getUnicornId());
        assertThat(unicornDto.name()).isEqualTo(savedUnicorn.getName());
        for (LegDto leg : unicornDto.legs()) {
            Leg savedLeg = savedUnicorn.getLeg(leg.legPosition());
            Assertions.assertThat(leg.color()).isEqualTo(savedLeg.getColor());
            Assertions.assertThat(leg.legSize()).isEqualTo(savedLeg.getLegSize());
        }
    }

    @Test
    void exceptionOnGetBecauseUnicornNotFound() {
        UnicornId unknownUnicornId = UnicornId.of("00000000-0000-0000-0000-000000000000");
        assertThatThrownBy(() -> unicornService.getUnicorn(unknownUnicornId)).isInstanceOf(UnicornNotFoundException.class);
    }

    @Test
    void findUnicornHealth() {
        unicornDao.add(new UnicornTestBuilder().healthyDefaults().unicornId(UnicornId.of("ffffffff-ffff-ffff-ffff-ffffffffffff")).build());
        UnicornDto unicornDto = unicornService.getUnicorn(UnicornId.of("ffffffff-ffff-ffff-ffff-ffffffffffff"));
        assertThat(unicornDto.health().getFinancialHealth()).isEqualTo(FinancialHealthDto.FinancialHealth.SUFFICIENT);
        assertThat(unicornDto.health().getPhysicalHealth()).isEqualTo(Unicorn.PhysicalHealth.MODERATE);
    }
}
