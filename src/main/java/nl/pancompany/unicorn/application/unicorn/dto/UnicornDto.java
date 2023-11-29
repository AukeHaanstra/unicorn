package nl.pancompany.unicorn.application.unicorn.dto;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;

import java.util.Set;

public record UnicornDto(Unicorn.UnicornId unicornId, String name, Set<LegDto> legs, HealthDto health) {

}
