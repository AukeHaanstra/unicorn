package nl.pancompany.unicorn.application;

import jakarta.validation.ConstraintViolationException;
import nl.pancompany.unicorn.ApplicationTestContext;
import nl.pancompany.unicorn.application.unicorn.repository.UnicornRepository;
import nl.pancompany.unicorn.application.unicorn.domain.model.Color;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.QueryLegDto;
import nl.pancompany.unicorn.application.unicorn.exception.UnicornNotFoundException;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.testbuilders.LegTestBuilder;
import nl.pancompany.unicorn.testbuilders.UnicornTestBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GetLegTest {

    UnicornRepository unicornRepository;
    UnicornLegService unicornLegService;
    Unicorn savedUnicorn;

    @BeforeEach
    void setup() {
        ApplicationTestContext applicationTestContext = new ApplicationTestContext();
        unicornRepository = applicationTestContext.getUnicornRepository();
        unicornLegService = applicationTestContext.getUnicornLegService();

        Leg newLeg = new LegTestBuilder()
                .legPosition(Leg.LegPosition.FRONT_LEFT)
                .color(Color.GREEN)
                .legSize(Leg.LegSize.SMALL)
                .build();
        savedUnicorn = unicornRepository.add(new UnicornTestBuilder()
                .defaults()
                .withLeg(newLeg)
                .build());
    }

    @Test
    void findsLeg() {
        var queryLegDto = new QueryLegDto(savedUnicorn.getUnicornId(), Leg.LegPosition.FRONT_LEFT);

        LegDto legDto = unicornLegService.getLeg(queryLegDto);

        Assertions.assertThat(legDto.legPosition()).isEqualTo(Leg.LegPosition.FRONT_LEFT);
        Assertions.assertThat(legDto.color()).isEqualTo(Color.GREEN);
        Assertions.assertThat(legDto.legSize()).isEqualTo(Leg.LegSize.SMALL);
    }

    @Test
    void exceptionOnGetLegBecauseUnicornIdMissingFromIdentity() {
        var queryLegDto = new QueryLegDto(null, Leg.LegPosition.FRONT_LEFT);

        assertThatThrownBy(() -> unicornLegService.getLeg(queryLegDto)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void exceptionOnGetLegBecauseLegPositionMissingFromIdentity() {
        var queryLegDto = new QueryLegDto(savedUnicorn.getUnicornId(), null);

        assertThatThrownBy(() -> unicornLegService.getLeg(queryLegDto)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void exceptionOnUpdateBecauseUnicornNotFound() {
        var queryLegDto = new QueryLegDto(Unicorn.UnicornId.of("00000000-0000-0000-0000-000000000000"), Leg.LegPosition.FRONT_LEFT);

        assertThatThrownBy(() -> unicornLegService.getLeg(queryLegDto)).isInstanceOf(UnicornNotFoundException.class);
    }

}
