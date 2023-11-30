package nl.pancompany.unicorn.application.unicorn.dto;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.service.UnicornEnrichmentService;

import java.util.Set;

public record UnicornDto(Unicorn.UnicornId unicornId, String name, Set<LegDto> legs, UnicornEnrichmentService.HealthDto health) {

}
