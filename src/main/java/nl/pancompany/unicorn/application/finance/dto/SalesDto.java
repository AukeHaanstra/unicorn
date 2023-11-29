package nl.pancompany.unicorn.application.finance.dto;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;

import java.util.UUID;

public record SalesDto(UnicornId unicornId, long salesTotal) {
}