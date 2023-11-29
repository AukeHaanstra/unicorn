package nl.pancompany.unicorn.application.unicorn.dto;

import jakarta.validation.constraints.NotNull;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;

public record QueryLegDto(@NotNull Unicorn.UnicornId unicornId, @NotNull Leg.LegPosition legPosition) {
}