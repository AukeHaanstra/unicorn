package nl.pancompany.unicorn.persistence.database;

import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.persistence.database.model.UnicornJpaEntity;
import nl.pancompany.unicorn.persistence.database.model.UnicornLegJpaEntity;
import org.mapstruct.*;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UnicornJpaMapper {

    UnicornJpaMapper INSTANCE = getMapper(UnicornJpaMapper.class);

    @Mapping(target = "unicornId", source = "unicornId", qualifiedByName = "mapUnicornId")
    Unicorn map(UnicornJpaEntity unicornJpaEntity);

    @Named("mapUnicornId")
    default UnicornId mapUnicornId(String value) {
        return UnicornId.of(value);
    }

    @Mapping(target = "id", ignore = true)
    UnicornJpaEntity map(Unicorn unicorn);

    default String mapUnicornId(UnicornId unicornId) {
        return unicornId == null ? null : unicornId.toStringValue();
    }

    @Mapping(target = "unicorn", ignore = true)
    @Mapping(target = "id", ignore = true)
    UnicornLegJpaEntity map(Leg leg);
}
