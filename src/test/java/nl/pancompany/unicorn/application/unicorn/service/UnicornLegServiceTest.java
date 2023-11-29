package nl.pancompany.unicorn.application.unicorn.service;

import nl.pancompany.unicorn.application.unicorn.dao.UnicornDao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.QueryLegDto;
import nl.pancompany.unicorn.application.unicorn.dto.UpdateLegDto;
import nl.pancompany.unicorn.application.unicorn.mapper.LegDtoMapper;
import nl.pancompany.unicorn.application.unicorn.domain.model.Color;
import nl.pancompany.unicorn.testbuilders.UnicornTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UnicornLegServiceTest {

    UnicornLegService unicornLegService;
    UnicornDao unicornDao;
    LegDtoMapper legDtoMapper;

    @BeforeEach
    public void setup() {
        unicornDao = mock(UnicornDao.class);
        legDtoMapper = mock(LegDtoMapper.class);
        unicornLegService = new UnicornLegService(unicornDao, legDtoMapper);
    }

    @Test
    public void canGetLeg() {
        var unicornId = Unicorn.UnicornId.generate();
        Unicorn unicorn = new UnicornTestBuilder().defaults().unicornId(unicornId).build();
        when(unicornDao.find(unicornId)).thenReturn(unicorn);
        var legDto = new LegDto(Leg.LegPosition.FRONT_LEFT, Color.CYAN, Leg.LegSize.SMALL);
        when(legDtoMapper.map(new Leg(Leg.LegPosition.FRONT_LEFT, Color.CYAN, Leg.LegSize.SMALL))).thenReturn(legDto);
        var queryLegDto = new QueryLegDto(unicornId, Leg.LegPosition.FRONT_LEFT);

        LegDto returnedLegDto = unicornLegService.getLeg(queryLegDto);

        verify(unicornDao).find(unicornId);
        assertThat(returnedLegDto).isEqualTo(legDto);
    }

    @Test
    public void canUpdateLeg() {
        var unicornId = Unicorn.UnicornId.generate();
        Unicorn unicorn = new UnicornTestBuilder().defaults().unicornId(unicornId)
                .withLeg(new Leg(Leg.LegPosition.FRONT_LEFT, Color.PURPLE, Leg.LegSize.SMALL)).build();
        when(unicornDao.find(unicornId)).thenReturn(unicorn);
        Unicorn patchedUnicorn = new UnicornTestBuilder().defaults().unicornId(unicornId)
                .withLeg(new Leg(Leg.LegPosition.FRONT_LEFT, Color.AUBURN, Leg.LegSize.SMALL)).build();
        when(unicornDao.update(patchedUnicorn)).thenReturn(patchedUnicorn);
        when(legDtoMapper.map(new Leg(Leg.LegPosition.FRONT_LEFT, Color.AUBURN, Leg.LegSize.SMALL)))
                .thenReturn(new LegDto(Leg.LegPosition.FRONT_LEFT, Color.AUBURN, Leg.LegSize.SMALL));
        var updateLegDto = new UpdateLegDto(unicornId, Leg.LegPosition.FRONT_LEFT, Color.AUBURN, Leg.LegSize.SMALL);

        LegDto updatedLegDto = unicornLegService.updateLeg(updateLegDto);

        assertThat(updatedLegDto.color()).isEqualTo(Color.AUBURN);
    }
}
