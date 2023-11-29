package nl.pancompany.unicorn.application.unicorn.dto;

import nl.pancompany.unicorn.application.unicorn.domain.model.Color;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;

public record LegDto(Leg.LegPosition legPosition, Color color, Leg.LegSize legSize) {

}
