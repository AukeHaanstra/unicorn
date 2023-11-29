package nl.pancompany.unicorn.application.unicorn.mapper;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, uses = LegDtoMapper.class)
public interface UnicornDtoMapper {

    // TODO: Seems like a bug in mapstruct
    //    @Mapping(target = "health.financialHealth", source="financialHealthDto.financialHealth")
    @Mapping(target = "health.financialHealth", ignore = true)

    @Mapping(target = "health.physicalHealth", source = "unicorn.physicalHealth")
    @Mapping(target = "unicornId", source = "unicorn.unicornId")
    @Mapping(target = ".", source = "unicorn")
    UnicornDto map(Unicorn unicorn, FinancialHealthDto financialHealthDto);
}
