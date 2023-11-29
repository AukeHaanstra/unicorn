package nl.pancompany.unicorn.web.model;

import nl.pancompany.unicorn.application.unicorn.dto.HealthDto;

import java.util.Set;

public record UnicornView(String unicornId, String name, Set<LegView> legs, HealthView health) {
}
