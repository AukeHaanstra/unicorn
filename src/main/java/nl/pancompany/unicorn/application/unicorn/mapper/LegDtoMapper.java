package nl.pancompany.unicorn.application.unicorn.mapper;

import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface LegDtoMapper {

    LegDto map(Leg leg);
}
