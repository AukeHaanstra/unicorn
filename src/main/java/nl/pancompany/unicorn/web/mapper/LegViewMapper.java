package nl.pancompany.unicorn.web.mapper;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.UpdateLegDto;
import nl.pancompany.unicorn.web.model.LegView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface LegViewMapper {

    LegView map(LegDto legDto);

    @Mapping(target = "unicornId", source = "unicornId", qualifiedByName = "mapUnicornId")
    UpdateLegDto map(LegView legDto, String unicornId);

    @Named("mapUnicornId")
    default UnicornId mapUnicornId(String unicornId) {
        return UnicornId.of(unicornId);
    }

}
