package nl.pancompany.unicorn.web.model;

import java.util.Set;

public record UnicornView(String unicornId, String name, Set<LegView> legs, HealthView health) {
}
