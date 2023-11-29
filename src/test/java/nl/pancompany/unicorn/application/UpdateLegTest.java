package nl.pancompany.unicorn.application;

import jakarta.validation.ConstraintViolationException;
import nl.pancompany.unicorn.application.unicorn.dao.UnicornDao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.UpdateLegDto;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornNotFoundException;
import nl.pancompany.unicorn.application.unicorn.domain.model.Color;
import nl.pancompany.unicorn.testbuilders.LegTestBuilder;
import nl.pancompany.unicorn.testbuilders.UnicornTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class UpdateLegTest {

    static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    @Autowired
    UnicornDao unicornDao;

    @Autowired
    UnicornLegService unicornLegService;

    Unicorn savedUnicorn;

    @BeforeEach
    public void setup() {
        Leg newLeg = new LegTestBuilder()
                .legPosition(Leg.LegPosition.FRONT_LEFT)
                .color(Color.GREEN)
                .legSize(Leg.LegSize.SMALL)
                .build();
        savedUnicorn = unicornDao.add(new UnicornTestBuilder()
                .defaults()
                .withLeg(newLeg)
                .build());
    }

    @Test
    void updatesLegColor() {
        var updateLegDto = new UpdateLegDto(savedUnicorn.getUnicornId(), Leg.LegPosition.FRONT_LEFT, Color.RED, Leg.LegSize.SMALL);
        unicornLegService.updateLeg(updateLegDto);

        Unicorn updatedUnicorn = unicornDao.find(savedUnicorn.getUnicornId());
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getColor()).isEqualTo(Color.RED);
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getLegSize()).isEqualTo(Leg.LegSize.SMALL);
    }

    @Test
    void updatesLegColorWithDifferentCaseID() {
        String uuidUppercase = UUID.randomUUID().toString().toUpperCase();
        Leg newLeg = new LegTestBuilder()
                .legPosition(Leg.LegPosition.FRONT_LEFT)
                .color(Color.GREEN)
                .legSize(Leg.LegSize.SMALL)
                .build();
        unicornDao.add(new UnicornTestBuilder()
                .defaults()
                .unicornId(Unicorn.UnicornId.of(uuidUppercase))
                .withLeg(newLeg)
                .build());
        var updateLegDto = new UpdateLegDto(Unicorn.UnicornId.of(uuidUppercase.toLowerCase()), Leg.LegPosition.FRONT_LEFT, Color.RED, Leg.LegSize.SMALL);
        unicornLegService.updateLeg(updateLegDto);

        Unicorn updatedUnicorn = unicornDao.find(Unicorn.UnicornId.of(uuidUppercase));
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getColor()).isEqualTo(Color.RED);
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getLegSize()).isEqualTo(Leg.LegSize.SMALL);
    }

    @Test
    void updatesLegColorWithDifferentCaseID2() {
        String uuidLowerCase = UUID.randomUUID().toString().toLowerCase();
        Leg newLeg = new LegTestBuilder()
                .legPosition(Leg.LegPosition.FRONT_LEFT)
                .color(Color.GREEN)
                .legSize(Leg.LegSize.SMALL)
                .build();
        unicornDao.add(new UnicornTestBuilder()
                .defaults()
                .unicornId(Unicorn.UnicornId.of(uuidLowerCase))
                .withLeg(newLeg)
                .build());
        var updateLegDto = new UpdateLegDto(Unicorn.UnicornId.of(uuidLowerCase.toUpperCase()), Leg.LegPosition.FRONT_LEFT, Color.RED, Leg.LegSize.SMALL);
        unicornLegService.updateLeg(updateLegDto);

        Unicorn updatedUnicorn = unicornDao.find(Unicorn.UnicornId.of(uuidLowerCase));
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getColor()).isEqualTo(Color.RED);
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getLegSize()).isEqualTo(Leg.LegSize.SMALL);
    }

    @Test
    void updatesLegSize() {
        var updateLegDto = new UpdateLegDto(savedUnicorn.getUnicornId(), Leg.LegPosition.FRONT_LEFT, Color.GREEN, Leg.LegSize.LARGE);
        unicornLegService.updateLeg(updateLegDto);

        Unicorn updatedUnicorn = unicornDao.find(savedUnicorn.getUnicornId());
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getColor()).isEqualTo(Color.GREEN);
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getLegSize()).isEqualTo(Leg.LegSize.LARGE);
    }

    @Test
    void updatesLegFully() {
        var updateLegDto = new UpdateLegDto(savedUnicorn.getUnicornId(), Leg.LegPosition.FRONT_LEFT, Color.RED, Leg.LegSize.LARGE);

        unicornLegService.updateLeg(updateLegDto);

        Unicorn updatedUnicorn = unicornDao.find(savedUnicorn.getUnicornId());
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getColor()).isEqualTo(Color.RED);
        assertThat(updatedUnicorn.getLeg(Leg.LegPosition.FRONT_LEFT).getLegSize()).isEqualTo(Leg.LegSize.LARGE);
    }

    @Test
    void exceptionOnUpdateBecauseLegPositionMissingFromIdentity() {
        var updateLegDto = new UpdateLegDto(savedUnicorn.getUnicornId(), null, Color.RED, Leg.LegSize.LARGE);

        assertThatThrownBy(() -> unicornLegService.updateLeg(updateLegDto)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void exceptionOnUpdateBecauseUnicornNotFound() {
        var updateLegDto = new UpdateLegDto(Unicorn.UnicornId.of(NIL_UUID), Leg.LegPosition.FRONT_LEFT, Color.RED, Leg.LegSize.LARGE);

        assertThatThrownBy(() -> unicornLegService.updateLeg(updateLegDto)).isInstanceOf(UnicornNotFoundException.class);
    }

}
