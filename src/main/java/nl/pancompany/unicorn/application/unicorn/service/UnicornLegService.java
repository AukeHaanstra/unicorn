package nl.pancompany.unicorn.application.unicorn.service;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.unicorn.application.unicorn.dao.Dao;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.QueryLegDto;
import nl.pancompany.unicorn.application.unicorn.dto.UpdateLegDto;
import nl.pancompany.unicorn.application.unicorn.mapper.LegDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UnicornLegService {

    private final Dao<Unicorn, Unicorn.UnicornId> unicornDao;
    private final LegDtoMapper legDtoMapper;

    public LegDto getLeg(@Valid QueryLegDto queryLegDto) throws UnicornNotFoundException, ConstraintViolationException {
        Unicorn unicorn = unicornDao.find(queryLegDto.unicornId());
        return legDtoMapper.map(unicorn.getLeg(queryLegDto.legPosition()));
    }

    public LegDto updateLeg(@Valid UpdateLegDto updateLegDto) throws UnicornNotFoundException, ConstraintViolationException {
        Unicorn unicorn = unicornDao.find(updateLegDto.unicornId());
        updateLeg(unicorn, updateLegDto);
        Unicorn updatedUnicorn = unicornDao.update(unicorn);
        log.info("Updated leg of unicorn with id={}", unicorn.getUnicornId().toStringValue());
        return legDtoMapper.map(updatedUnicorn.getLeg(updateLegDto.legPosition()));
    }

    public void updateLeg(Unicorn unicorn, UpdateLegDto updateLegDto) {
        unicorn.setLegColor(updateLegDto.legPosition(), updateLegDto.color());
        unicorn.setLegSize(updateLegDto.legPosition(), updateLegDto.legSize());
    }

}
